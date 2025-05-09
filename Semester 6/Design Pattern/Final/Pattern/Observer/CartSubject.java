
import java.util.ArrayList;
import java.util.List;

public class CartSubject {
    private final List<CartObserver> observers = new ArrayList<>();
    private Integer accountId; // Trạng thái: ID tài khoản sở hữu giỏ hàng
    private int itemCount; // Trạng thái: Số lượng sản phẩm trong giỏ hàng

    public void attach(CartObserver observer) {
        observers.add(observer);
    }

    public void detach(CartObserver observer) {
        observers.remove(observer);
    }

    protected void notifyCartUpdated(Integer accountId) {
        for (CartObserver observer : observers) {
            observer.updateCart(accountId);
        }
    }

    // Phương thức để cập nhật giỏ hàng và kích hoạt thông báo
    public void updateCart(Integer accountId, int newItemCount) {
        this.accountId = accountId;
        this.itemCount = newItemCount;
        notifyCartUpdated(accountId);
    }

    // Getter cho trạng thái (nếu cần)
    public int getItemCount() {
        return itemCount;
    }
}