
public class CartWebSocketNotifier implements CartObserver {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void updateCart(Integer accountId) {
        // Gửi thông báo qua WebSocket cho client
        String destination = "/topic/cart/" + accountId;
        messagingTemplate.convertAndSend(destination, "Cart updated for account ID: " + accountId);
    }
}