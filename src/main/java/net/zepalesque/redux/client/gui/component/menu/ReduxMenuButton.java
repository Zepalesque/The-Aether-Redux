package net.zepalesque.redux.client.gui.component.menu;

import com.aetherteam.aether.client.gui.component.Builder;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.gui.screen.menu.ReduxTitleScreen;

/** NOTE: Duplicates {@link com.aetherteam.aether.client.gui.component.menu.AetherMenuButton}, with the only difference being no 'alignedLeft' value used (always left-aligned) and a different texture, along with using a {@link ReduxTitleScreen} */
public class ReduxMenuButton extends Button {
	private static final ResourceLocation AETHER_WIDGETS = Redux.locate("textures/gui/title/buttons.png");
	private static final int BUTTON_WIDTH = 400;
	private static final int BUTTON_HEIGHT = 40;
	private static final int BUTTON_SEPARATION = 50;
	private static final int INITIAL_X_OFFSET = 16;
	private static final int INITIAL_Y_OFFSET = 100;
	private static final int TEXTURE_SIZE = 512;
	private final ReduxTitleScreen screen;
	public final int originalX;
	public final int originalY;
	public final int originalWidth;
	public final int originalHeight;
	public int hoverOffset;
	public int buttonCountOffset;
	public boolean serverButton;

	public ReduxMenuButton(ReduxTitleScreen screen, Builder builder) {
		super(builder.x, builder.y, builder.width, builder.height, builder.message, builder.onPress, builder.tooltip);
		this.screen = screen;
		this.originalX = this.x;
		this.originalY = this.y;
		this.originalWidth = this.getWidth();
		this.originalHeight = this.getHeight();
		this.hoverOffset = 0;
	}

	public ReduxMenuButton(ReduxTitleScreen screen, Button oldButton) {
		this(screen, (new Builder(oldButton.getMessage(), (button) -> {
			oldButton.onPress();
		})).bounds(oldButton.x, oldButton.y, oldButton.getWidth(), oldButton.getHeight()));
		oldButton.visible = false;
		oldButton.active = false;
	}


	@Override
	public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
		Minecraft minecraft = Minecraft.getInstance();
		Font font = minecraft.font;
		int i = this.getTextureY();
		float scale = ReduxTitleScreen.getScale(this.screen, minecraft);
		this.x = 16;
		this.y = (int)(100.0F / scale + (float)this.buttonCountOffset * (50.0F / scale));
		this.setWidth((int)(400.0F / scale));

		this.setHeight((int)(40.0F / scale));
		RenderSystem.setShaderTexture(0, AETHER_WIDGETS);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();
		GuiComponent.blit(poseStack, this.x + this.hoverOffset, this.y, 0.0F, (float)Mth.ceil((float)i / scale), this.getWidth(), this.getHeight(), (int)(512.0F / scale), (int)(512.0F / scale));
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.disableDepthTest();
		RenderSystem.disableBlend();
		poseStack.pushPose();
		float textScale = getTextScale(this.screen, minecraft);
		float textX = (float)this.x + 35.0F * textScale + (float)this.hoverOffset;
		float textY = (float)this.y + ((float)this.height - 8.0F * textScale) / 2.0F;
		poseStack.translate((double)textX, (double)textY, 0.0);
		poseStack.scale(textScale, textScale, textScale);
		GuiComponent.drawString(poseStack, font, this.getMessage(), 0, 0, this.getTextColor(mouseX, mouseY) | Mth.ceil(this.alpha * 255.0F) << 24);
		poseStack.popPose();
	}

	public static float getTextScale(ReduxTitleScreen screen, Minecraft minecraft) {
		int guiScale = minecraft.getWindow().calculateScale(minecraft.options.guiScale().get(), minecraft.isEnforceUnicode()); // The true screen GUI scale.
		float elementScale = ReduxTitleScreen.getScale(screen, minecraft); // The scaling for elements relative to the true screen scale.
		float elementPixelWidth = (int) (guiScale / elementScale); // How many pixels-per-pixel for a rendered element.
		float textPixelWidth = elementPixelWidth + 2.0F; // How many pixels-per-pixel for text.
		if (elementPixelWidth <= 1) {
			textPixelWidth = 2.0F;
		}
		return textPixelWidth / guiScale; // Get the scaling for text relative to the amount of pixels-per-pixel that this text should have when rendering.
	}

	private int getTextureY() {
		int i = 1;
		if (!this.isActive()) {
			i = 0;
		} else if (this.isHoveredOrFocused()) {
			i = 2;
		}
		return i * 40;
	}
	public int getTextColor(int mouseX, int mouseY) {
		if (!this.serverButton) {
			return this.isMouseOver(mouseX, mouseY) ? 0xD6D6FF : 0xFFFFFF;
		} else {
			return this.isMouseOver(mouseX, mouseY) ? 0xF2DF52 : 0xFFEC60;
		}
	}

	public static int totalHeightRange(int buttonCount, float scale) {
		return (int) ((INITIAL_Y_OFFSET / scale) + ((buttonCount) * ((BUTTON_SEPARATION + 10) / scale)));
	}
}