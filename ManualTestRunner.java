import java.util.List;
import DataModels.Order;
import DataModels.Product;
import DecoratorPattern.GiftWrapDecorator;
import DecoratorPattern.InsuranceDecorator;
import FactoryMethodPattern.Shipment;
import FactoryMethodPattern.ShipmentFactory;
import ObserverPattern.*;
import StrategyPattern.DiscountStrategy;
import StrategyPattern.FixedDiscount;
import StrategyPattern.OrderCalculator;
import StrategyPattern.PercentageDiscount;


public class ManualTestRunner {
    public static void main(String[] args) {
        System.out.println("--- E-commerce System Simulation ---");

        // --- 1. Setup ---
        Product laptop = new Product("P001", "Laptop",30000.0);
        Product mouse = new Product("P002","Mouse",800.0);
        Order myOrder = new Order("OPD-001", List.of(laptop,mouse), "customer@example.com");

        OrderCalculator calculator = new OrderCalculator();
        ShipmentFactory shipmentFactory = new ShipmentFactory();

        OrderProcessor orderProcessor = new OrderProcessor();
        InventoryService inventory = new InventoryService();
        EmailService emailer = new EmailService();
        orderProcessor.register(inventory);
        orderProcessor.register(emailer);

        System.out.println("\n--- 2. Testing Strategy Pattern (Discounts) ---");
        double originalPrice = myOrder.getTotalPrice();
        System.out.println("Original Price: " + originalPrice);

        DiscountStrategy tenPercentOff = new PercentageDiscount(10);
        double priceAfterPercentage = calculator.calculateFinalPrice(myOrder, tenPercentOff);
        System.out.println("Price with 10% discount:" + priceAfterPercentage);

        DiscountStrategy fiveHundredOff = new FixedDiscount(500);
        double priceAfterFixed = calculator.calculateFinalPrice(myOrder, fiveHundredOff);
        System.out.println("Price with 500 THB discount: " + priceAfterFixed);

        System.out.println("\n---3. Testing Factory and Decorator Patterns (Shipment) --- ");
        //สร้างการส่งแบบมาตรฐาน
        Shipment standardShipment = shipmentFactory.createShipment("STANDARD");
        System.out.println("Base Shipment: " + standardShipment.getInfo() + ", Cost: " + standardShipment.getCost());

        // "ห่อ" ด้วยการบริการของขวัญ
        Shipment giftWrapped = new GiftWrapDecorator(standardShipment);
        System.out.println("Decorated: " + giftWrapped.getInfo() + ", Cost: " + giftWrapped.getCost());

        // "ห่อ" ทับด้วยบริการประกันสินค้า
        Shipment fullyLoaded = new InsuranceDecorator(standardShipment, myOrder);
        System.out.println("Fully Decorated: " + fullyLoaded.getInfo() + ", Cost: " + fullyLoaded.getCost());

        System.out.println("\n--- 4. Printing Final Summary ---");
        double finalPrice = priceAfterFixed;// สมมติว่าใช้ส่วนลด 10%
        double tatalCost = finalPrice + fullyLoaded.getCost();
        System.out.println("Final price after discount: " + finalPrice);

        //--- 5. Testing Observer Patten (Processing Order) ---
        orderProcessor.processOrder(myOrder);
    }
}
