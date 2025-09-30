package com.company.samplesalessecurity.listener;

import com.company.samplesalessecurity.app.SpecialProductCount;
import com.company.samplesalessecurity.entity.Order;
import com.company.samplesalessecurity.entity.OrderLine;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.core.event.EntityChangedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component("sales_OrderLineEventListener")
public class OrderLineEventListener {

    private final DataManager dataManager;
    private final SpecialProductCount specialProductCount;

    public OrderLineEventListener(DataManager dataManager,
                                  SpecialProductCount specialProductCount) {
        this.dataManager = dataManager;
        this.specialProductCount = specialProductCount;
    }

    @EventListener
    public void onOrderLineChangedBeforeCommit(final EntityChangedEvent<OrderLine> event) {
        Order order = null;
        if (event.getType() != EntityChangedEvent.Type.DELETED){
            order = dataManager.load(event.getEntityId())
                    .fetchPlan("orderLine-with-order")
                    .one()
                    .getOrder();
        } else {
            Id<Order> orderUUIDId = event.getChanges().getOldReferenceId("order");
            order = dataManager.load(orderUUIDId).optional().orElse(null);
            if (order == null){
                // the order could be deleted too
                return;
            }
        }

        order.setNumberOfSpecialProducts(specialProductCount.getSpecialProductsNumber(order));
        dataManager.save(order);
    }
}