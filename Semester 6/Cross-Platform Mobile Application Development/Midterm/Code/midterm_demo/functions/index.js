const {onCall} = require("firebase-functions/v2/https");
const logger = require("firebase-functions/logger");
const admin = require("firebase-admin");
admin.initializeApp();

// 🔥 Danh sách từ bậy cần lọc (có thể mở rộng thêm)
const badWords = [ "chửi", "bậy", "tục"];

// ✅ Cloud Function lọc từ bậy bạ
exports.filterAndSendMessage = onCall(async (request) => {
  const { chatId, senderId, content } = request.data;

  if (!chatId || !senderId || !content) {
    throw new Error("Thiếu dữ liệu đầu vào");
  }

  // ⚡ Lọc nội dung
  let filteredContent = content;
  badWords.forEach((word) => {
    const regex = new RegExp(`${word}`, "gi");
    filteredContent = filteredContent.replace(regex, "***");
  });

  const messageData = {
    senderId,
    content: filteredContent,
    timestamp: Math.floor(Date.now() / 1000),
  };

  // 🔄 Lưu tin nhắn vào Realtime Database
  const messageRef = admin.database().ref(`chats/${chatId}/messages`).push();
  await messageRef.set(messageData);

  // 🕒 Cập nhật lastMessage
  await admin.database().ref(`chats/${chatId}/lastMessage`).set(messageData);

  logger.info("Tin nhắn đã được lọc và gửi thành công");
  return { success: true };
});
