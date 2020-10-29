package net.thirdshift.paulstweaks.screen.bookbinder;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandlerContext;
import net.thirdshift.paulstweaks.PaulsTweaks;

public class BookBinderGuiDescription extends SyncedGuiDescription {
	private static final int INVENTORY_SIZE = 4;

	public BookBinderGuiDescription(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context){
		super(PaulsTweaks.BOOK_BINDER_SCREEN_HANDLER_TYPE, syncId, playerInventory, getBlockInventory(context, INVENTORY_SIZE), getBlockPropertyDelegate(context));

		WGridPanel root = new WGridPanel();
		setRootPanel(root);
		root.setSize(150,150);

		WItemSlot leftItemSlots = WItemSlot.of(blockInventory, 0, 3,1);
		root.add(leftItemSlots, 0, 1);

		WItemSlot outputSlot = WItemSlot.of(blockInventory, 3, 1, 1);
		root.add(outputSlot, 5, 1);

		root.add(this.createPlayerInventoryPanel(), 0, 4);

		root.validate(this);
	}

}
