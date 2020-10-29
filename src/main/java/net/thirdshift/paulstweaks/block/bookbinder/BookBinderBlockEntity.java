package net.thirdshift.paulstweaks.block.bookbinder;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;
import net.thirdshift.paulstweaks.PaulsTweaks;
import net.thirdshift.paulstweaks.inventory.ImplementedInventory;
import net.thirdshift.paulstweaks.screen.bookbinder.BookBinderGuiDescription;
import org.jetbrains.annotations.Nullable;

public class BookBinderBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory, SidedInventory, Tickable {
	private final DefaultedList<ItemStack> inventory;
	private short leatherUses;

	public BookBinderBlockEntity() {
		super(PaulsTweaks.BOOK_BINDER_BLOCK_ENTITY);
		inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);
		leatherUses = 0;
	}

	@Override
	public void fromTag(BlockState state, CompoundTag tag) {
		super.fromTag(state, tag);
		Inventories.fromTag(tag, inventory);
		leatherUses = tag.getShort("leatherUses");
	}

	@Override
	public CompoundTag toTag(CompoundTag tag) {
		Inventories.toTag(tag, inventory);
		tag.putShort("leatherUses", leatherUses);
		return super.toTag(tag);
	}

	@Override
	public DefaultedList<ItemStack> getItems() {
		return inventory;
	}

	@Override
	public int[] getAvailableSlots(Direction side) {
		int[] result = new int[getItems().size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = i;
		}

		return result;
	}

	@Override
	public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
		return slot != 3;
	}

	@Override
	public boolean canExtract(int slot, ItemStack stack, Direction dir) {
		return dir == Direction.DOWN || dir == Direction.EAST;
	}

	@Override
	public void tick() {
		if(!hasLeather())
			return;
		System.out.println("has leather!");
	}

	private boolean hasLeather(){
		for (int i = 0; i < 2; i++) {
			if(getStack(i).isItemEqual(Items.LEATHER.getDefaultStack()) && getStack(i).getCount() > 0){
				return true;
			}
		}
		return false;
	}

	@Override
	public Text getDisplayName() {
		return new TranslatableText(getCachedState().getBlock().getTranslationKey());
	}

	@Override
	public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
		return new BookBinderGuiDescription(syncId, inv, ScreenHandlerContext.create(world,pos));
	}
}
