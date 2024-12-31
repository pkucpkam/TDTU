import java.util.List;

/**
 * Interface for creating repository classes
 * Please create your own repository and implement this interface
 * @param <T> type of object managed by the repository (e.g. Product)
 * @param <K> type of the object's primary key (e.g. Integer or String)
 */
public interface Repository <T, K>{
    K add(T item);
    List<T> readAll();
    T read(K id);
    boolean update(T item);
    boolean delete(K id);
}
