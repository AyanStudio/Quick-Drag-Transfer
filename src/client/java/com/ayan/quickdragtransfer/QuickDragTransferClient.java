package com.ayan.quickdragtransfer;

import java.util.HashSet;
import java.util.Set;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;

/**
 * Hold Shift + Left Click and drag the cursor across inventory slots: every
 * item-holding slot you pass over gets quick-transferred (the same move a
 * plain Shift + Left Click already does), without clicking each one by hand.
 *
 * <p>This only listens to mouse events on container-style screens (chests,
 * furnaces, the player inventory, etc.) via Fabric API's {@link ScreenMouseEvents}.
 * No mixin is used; the only non-public thing this mod touches is the
 * {@code hoveredSlot} field on {@link AbstractContainerScreen}, exposed
 * through {@code quickdragtransfer.accesswidener}.
 */
public class QuickDragTransferClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
			if (!(screen instanceof AbstractContainerScreen<?> containerScreen)) {
				return;
			}

			// Slots already transferred during the drag currently in progress,
			// so lingering over one slot doesn't fire the transfer repeatedly.
			Set<Integer> handledThisDrag = new HashSet<>();

			ScreenMouseEvents.afterMouseDrag(screen).register((s, event, horizontalAmount, verticalAmount, consumed) -> {
				// Note: we deliberately ignore "consumed" here - AbstractContainerScreen's
				// own mouseDragged reports the drag as consumed just for being inside the
				// screen at all, which isn't the same as "nothing left for us to add".
				if (event.button() != 0 || !event.hasShiftDown()) {
					// Only a Shift-held left-click drag triggers a transfer.
					return false;
				}
				if (!containerScreen.getMenu().getCarried().isEmpty()) {
					// Something is already picked up on the cursor (mid quick-craft
					// drag) - let vanilla's own drag handling deal with that instead.
					return false;
				}

				Slot slot = containerScreen.hoveredSlot;
				if (slot == null || !slot.hasItem()) {
					return false;
				}
				if (!handledThisDrag.add(slot.index)) {
					return false;
				}

				Minecraft mc = Minecraft.getInstance();
				MultiPlayerGameMode gameMode = mc.gameMode;
				Player player = mc.player;
				if (gameMode == null || player == null) {
					return false;
				}

				gameMode.handleContainerInput(
						containerScreen.getMenu().containerId,
						slot.index,
						0,
						ContainerInput.QUICK_MOVE,
						player
				);
				return true;
			});

			ScreenMouseEvents.afterMouseRelease(screen).register((s, event, consumed) -> {
				handledThisDrag.clear();
				return false;
			});
		});
	}
}
