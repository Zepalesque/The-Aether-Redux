package net.zepalesque.redux.client.gui.screen.menu;

import com.aetherteam.aether.client.gui.component.menu.AetherMenuButton;
import com.aetherteam.aether.client.gui.component.menu.DynamicMenuButton;
import com.aetherteam.aether.client.gui.screen.menu.TitleScreenBehavior;
import com.aetherteam.aether.mixin.mixins.client.accessor.TitleScreenAccessor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.client.ForgeHooksClient;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.gui.component.menu.ReduxMenuButton;

import java.util.Iterator;

/** NOTE: Duplicates {@link com.aetherteam.aether.client.gui.screen.menu.AetherTitleScreen}, with a few edits such as always being left-aligned (except the logo) and different button textures and stuff */
public class ReduxTitleScreen extends TitleScreen implements TitleScreenBehavior {
	private static final ResourceLocation PANORAMA_OVERLAY = new ResourceLocation("textures/gui/title/background/panorama_overlay.png");
	private static final ResourceLocation AETHER_LOGO = Redux.locate("textures/gui/title/redux.png");
	private final PanoramaRenderer cube;
	private int rows;
	private static final int baseLogoHeight = 144;
	// TODO: Custom splash text?
	private static final int SPLASH_COLOR = 0xAAAAFF;
	private static final int baseLogoWidth = baseLogoHeight * 2;

	public ReduxTitleScreen(String panorama) {
		((TitleScreenAccessor) this).aether$setFading(true);
		this.cube = new PanoramaRenderer(new CubeMap(Redux.locate("textures/gui/title/panorama/" + panorama + "/panorama")));
	}




	@Override
	protected void init() {
		super.init();
		this.setupButtons();
	}

	public void setupButtons() {
		int buttonRows = 0;
		int lastY = 0;
		Iterator var3 = this.renderables.iterator();

		while(var3.hasNext()) {
			Widget renderable = (Widget)var3.next();
			if (renderable instanceof AbstractWidget abstractWidget) {
				if (TitleScreenBehavior.isImageButton(abstractWidget.getMessage())) {
					abstractWidget.visible = false;
				}

				if (abstractWidget instanceof ReduxMenuButton aetherMenuButton) {
					++buttonRows;

					aetherMenuButton.buttonCountOffset = buttonRows;
				}
			}
		}

		this.rows = buttonRows;
	}

	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		TitleScreenAccessor titleScreenAccessor = (TitleScreenAccessor) this;
		float fadeAmount = TitleScreenBehavior.super.handleFading(poseStack, this, titleScreenAccessor, this.cube, PANORAMA_OVERLAY, partialTicks);
		float scale = getScale(this, this.getMinecraft());
		this.setupLogo(poseStack, fadeAmount, scale);
		int roundedFadeAmount = Mth.ceil(fadeAmount * 255.0F) << 24;
		if ((roundedFadeAmount & -67108864) != 0) {
			ForgeHooksClient.renderMainMenu(this, poseStack, this.font, this.width, this.height, roundedFadeAmount);
			if (titleScreenAccessor.aether$getSplash() != null) {
				float splashX = (float) ReduxTitleScreen.this.width / 2 + (baseLogoHeight / scale);
				float splashY = (int) (20 + ((baseLogoHeight - 24) / scale));
				poseStack.pushPose();
				poseStack.translate(splashX, splashY, 0.0F);
				poseStack.mulPose(Vector3f.ZP.rotationDegrees(-20.0F));
				float textSize = 1.4F - Mth.abs(Mth.sin((float) (Util.getMillis() % 1000L) / 1000.0F * Mth.TWO_PI) * 0.1F);
				String splash = titleScreenAccessor.aether$getSplash();
				textSize = textSize * (200.0F / scale) / (ReduxTitleScreen.this.font.width(splash) + (64 / scale));
				poseStack.scale(textSize, textSize, textSize);
				GuiComponent.drawCenteredString(poseStack, ReduxTitleScreen.this.font, splash, 0, (int) (-16 / scale), SPLASH_COLOR | roundedFadeAmount);
				poseStack.popPose();
			}

			TitleScreenBehavior.super.renderRightBranding(poseStack, this, this.font, roundedFadeAmount);

		}

		int xOffset = TitleScreenBehavior.super.handleButtonVisibility(this, fadeAmount);

		for (Widget renderable : this.renderables) {
			renderable.render(poseStack, mouseX, mouseY, partialTicks);
			if (renderable instanceof AetherMenuButton aetherButton) {
				if (aetherButton.isMouseOver((double) mouseX, (double) mouseY)) {
					if (aetherButton.hoverOffset < 15) {
						aetherButton.hoverOffset += 4;
					}
				} else if (aetherButton.hoverOffset > 0) {
					aetherButton.hoverOffset -= 4;
				}
			}

			if (renderable instanceof DynamicMenuButton dynamicMenuButton) {
				if (dynamicMenuButton.enabled) {
					xOffset -= 24;
				}
			}
		}

		TitleScreenBehavior.super.handleImageButtons(this, xOffset);
	}

	/**
	 * Renders the Aether: Redux logo on the title screen.
	 * @param poseStack The rendering {@link PoseStack}.
	 * @param transparency The transparency {@link Float} for the logo.
	 * @param scale The {@link Float} for the scaling of the logo relative to the true screen scale.
	 */
	private void setupLogo(PoseStack poseStack, float transparency, float scale) {
		RenderSystem.setShaderTexture(0, AETHER_LOGO);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, transparency);
		int width = (int) (baseLogoWidth / scale);
		int height = (int) (baseLogoHeight / scale);
		int logoX = (int) ((this.width / 2 - (baseLogoWidth / 2) / scale));
		int logoY = (int) (/*25*/ 0 + (10 / scale));
		GuiComponent.blit(poseStack, logoX, logoY, 0.0F, 0.0F, width, height, width, height);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
	}

	/**
	 * Determines the proper scaling for menu elements relative to the true screen scale.
	 * @param screen The parent {@link ReduxTitleScreen}.
	 * @param minecraft The {@link Minecraft} instance.
	 * @return The {@link Float} scale for menu elements.
	 */
	public static float getScale(ReduxTitleScreen screen, Minecraft minecraft) {
		int guiScale = minecraft.getWindow().calculateScale(minecraft.options.guiScale().get(), minecraft.isEnforceUnicode());  // The true screen GUI scale.
		return calculateScale(screen, guiScale, guiScale - 1);
	}
	/**
	 * Determines the proper scaling for menu elements relative to the given scale factors.
	 * @param screen The parent {@link ReduxTitleScreen}.
	 * @param guiScale The base GUI scale {@link Float}.
	 * @param lowerScale A GUI scale {@link Float} value that is one less than the base.
	 * @return The {@link Float} scale for menu elements.
	 */
	public static float calculateScale(ReduxTitleScreen screen, float guiScale, float lowerScale) {
		float scale = 1.0F;
		if (guiScale > 1) {
			scale = guiScale / lowerScale; // A scale factor to counteract the GUI scale option's changing of menu element's pixel scale (pixels-per-pixel).
		}
		int range = ReduxMenuButton.totalHeightRange(screen.rows, scale);
		if (range > screen.height && scale != 1.0F) { // Recursive check to see if the menu elements can actually fit on the screen, otherwise it'll try to shrink to a lower GUI scale.
            return calculateScale(screen, guiScale, lowerScale - 1);
		} else {
			return scale;
		}
	}

	/**
	 * Changes main menu buttons into Aether-styled main menu buttons.<br><br>
	 * Warning for "unchecked" is suppressed because the buttons should always be able to be cast.
	 * @param renderable A renderable widget.
	 * @return A new renderable widget.
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected <T extends GuiEventListener & Widget & NarratableEntry> T addRenderableWidget(T renderable) {
		if (renderable instanceof Button button) {
			if (TitleScreenBehavior.isMainButton(button.getMessage())) {
				ReduxMenuButton aetherButton = new ReduxMenuButton(this, button);
				return (T) super.addRenderableWidget(aetherButton);
			}
		}

		return super.addRenderableWidget(renderable);
	}

	@Override
	public void onClose() {
		super.onClose();
	}
}