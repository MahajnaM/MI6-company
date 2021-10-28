package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.messages.GadgetAvailableEvent;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.passiveObjects.Inventory;

/**
 * Q is the only Subscriber\Publisher that has access to the {@link bgu.spl.mics.application.passiveObjects.Inventory}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Q extends Subscriber {

	private Inventory inventory;
	private int tick;

	public Q() {
		super("q");
		tick=0;
		inventory = Inventory.getInstance();
	}

	@Override
	protected void initialize() {
		subscribeBroadcast(TickBroadcast.class,b->{tick=b.getTick();});
		this.subscribeEvent(GadgetAvailableEvent.class, (event) -> {
			String g = event.getGadget();
			boolean goodGadget = inventory.getItem(g);
			System.out.println("Q: "+goodGadget);
			int result = goodGadget ? tick : -1;
			complete(event, result);
		});
	}
}
