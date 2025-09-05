package ObserverPattern;
import DataModels.Order;

/*
 * Interface (Observer) สำหรับผู้สังเกตการณ์
 */
public interface OrderObserver {
    void update(Order order);
}
