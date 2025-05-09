
@Component
public class CartUpdateSubject extends CartSubject {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    protected void notifyCartUpdated(Integer accountId) {
        // Gọi phương thức cha để thông báo các Observer
        super.notifyCartUpdated(accountId);
        // Gửi thông báo qua WebSocket
        String destination = "/topic/cart/" + accountId;
        messagingTemplate.convertAndSend(destination, "Cart updated with " + getItemCount() + " items");
    }
}