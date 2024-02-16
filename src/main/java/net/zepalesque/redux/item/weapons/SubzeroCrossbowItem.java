package net.zepalesque.redux.item.weapons;

import com.aetherteam.aether.item.AetherItems;
import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.zepalesque.redux.capability.arrow.SubzeroArrow;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.List;

public class SubzeroCrossbowItem extends CrossbowItem {
   /** Set to {@code true} when the crossbow is 20% charged. */
   private boolean startSoundPlayed = false;
   /** Set to {@code true} when the crossbow is 50% charged. */
   private boolean midLoadSoundPlayed = false;

   public SubzeroCrossbowItem(Item.Properties pProperties) {
      super(pProperties);
   }

   /**
    * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
    * .
    */
   @Override
   public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
      ItemStack itemstack = pPlayer.getItemInHand(pHand);
      if (isCharged(itemstack)) {
         performShooting(pLevel, pPlayer, pHand, itemstack, getShootingPower(itemstack), 1.0F);
         setCharged(itemstack, false);
         return InteractionResultHolder.consume(itemstack);
      } else if (!pPlayer.getProjectile(itemstack).isEmpty()) {
         if (!isCharged(itemstack)) {
            this.startSoundPlayed = false;
            this.midLoadSoundPlayed = false;
            pPlayer.startUsingItem(pHand);
         }

         return InteractionResultHolder.consume(itemstack);
      } else {
         return InteractionResultHolder.fail(itemstack);
      }
   }

   private static float getShootingPower(ItemStack pCrossbowStack) {
      return containsChargedProjectile(pCrossbowStack, Items.FIREWORK_ROCKET) ? 1.6F : 3.15F;
   }

   /**
    * Called when the player stops using an Item (stops holding the right mouse button).
    */
   @Override

   public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
      int i = this.getUseDuration(pStack) - pTimeLeft;
      float f = getPowerForTime(i, pStack);
      if (f >= 1.0F && !isCharged(pStack) && tryLoadProjectiles(pEntityLiving, pStack)) {
         setCharged(pStack, true);
         SoundSource soundsource = pEntityLiving instanceof Player ? SoundSource.PLAYERS : SoundSource.HOSTILE;
         pLevel.playSound(null, pEntityLiving.getX(), pEntityLiving.getY(), pEntityLiving.getZ(), ReduxSoundEvents.SUBZERO_CROSSBOW_LOADING_END.get(), soundsource, 1.0F, 1.0F / (pLevel.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F);
      }

   }

   private static boolean tryLoadProjectiles(LivingEntity pShooter, ItemStack pCrossbowStack) {
      int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, pCrossbowStack);
      int j = i == 0 ? 1 : 3;
      boolean flag = pShooter instanceof Player && ((Player)pShooter).getAbilities().instabuild;
      ItemStack itemstack = pShooter.getProjectile(pCrossbowStack);
      ItemStack itemstack1 = itemstack.copy();

      for(int k = 0; k < j; ++k) {
         if (k > 0) {
            itemstack = itemstack1.copy();
         }

         if (itemstack.isEmpty() && flag) {
            itemstack = new ItemStack(Items.ARROW);
            itemstack1 = itemstack.copy();
         }

         if (!loadProjectile(pShooter, pCrossbowStack, itemstack, k > 0, flag)) {
            return false;
         }
      }

      return true;
   }

   private static boolean loadProjectile(LivingEntity pShooter, ItemStack pCrossbowStack, ItemStack pAmmoStack, boolean pHasAmmo, boolean pIsCreative) {
      if (pAmmoStack.isEmpty()) {
         return false;
      } else {
         boolean flag = pIsCreative && pAmmoStack.getItem() instanceof ArrowItem;
         ItemStack itemstack;
         if (!flag && !pIsCreative && !pHasAmmo) {
            itemstack = pAmmoStack.split(1);
            if (pAmmoStack.isEmpty() && pShooter instanceof Player) {
               ((Player)pShooter).getInventory().removeItem(pAmmoStack);
            }
         } else {
            itemstack = pAmmoStack.copy();
         }

         addChargedProjectile(pCrossbowStack, itemstack);
         return true;
      }
   }

   public static boolean isCharged(ItemStack pCrossbowStack) {
      CompoundTag compoundtag = pCrossbowStack.getTag();
      return compoundtag != null && compoundtag.getBoolean("Charged");
   }

   public static void setCharged(ItemStack pCrossbowStack, boolean pIsCharged) {
      CompoundTag compoundtag = pCrossbowStack.getOrCreateTag();
      compoundtag.putBoolean("Charged", pIsCharged);
   }

   private static void addChargedProjectile(ItemStack pCrossbowStack, ItemStack pAmmoStack) {
      CompoundTag compoundtag = pCrossbowStack.getOrCreateTag();
      ListTag listtag;
      if (compoundtag.contains("ChargedProjectiles", 9)) {
         listtag = compoundtag.getList("ChargedProjectiles", 10);
      } else {
         listtag = new ListTag();
      }

      CompoundTag compoundtag1 = new CompoundTag();
      pAmmoStack.save(compoundtag1);
      listtag.add(compoundtag1);
      compoundtag.put("ChargedProjectiles", listtag);
   }

   private static List<ItemStack> getChargedProjectiles(ItemStack pCrossbowStack) {
      List<ItemStack> list = Lists.newArrayList();
      CompoundTag compoundtag = pCrossbowStack.getTag();
      if (compoundtag != null && compoundtag.contains("ChargedProjectiles", 9)) {
         ListTag listtag = compoundtag.getList("ChargedProjectiles", 10);
         if (listtag != null) {
            for(int i = 0; i < listtag.size(); ++i) {
               CompoundTag compoundtag1 = listtag.getCompound(i);
               list.add(ItemStack.of(compoundtag1));
            }
         }
      }

      return list;
   }



   private static void clearChargedProjectiles(ItemStack pCrossbowStack) {
      CompoundTag compoundtag = pCrossbowStack.getTag();
      if (compoundtag != null) {
         ListTag listtag = compoundtag.getList("ChargedProjectiles", 9);
         listtag.clear();
         compoundtag.put("ChargedProjectiles", listtag);
      }

   }

   private static void shootProjectile(Level pLevel, LivingEntity pShooter, InteractionHand pHand, ItemStack pCrossbowStack, ItemStack pAmmoStack, float pSoundPitch, boolean pIsCreativeMode, float pVelocity, float pInaccuracy, float pProjectileAngle) {
      if (!pLevel.isClientSide) {
         boolean flag = pAmmoStack.is(Items.FIREWORK_ROCKET);
         Projectile projectile;
         if (flag) {
            projectile = new FireworkRocketEntity(pLevel, pAmmoStack, pShooter, pShooter.getX(), pShooter.getEyeY() - (double)0.15F, pShooter.getZ(), true);
         } else {
            projectile = getArrow(pLevel, pShooter, pCrossbowStack, pAmmoStack);
            if (pIsCreativeMode || pProjectileAngle != 0.0F) {
               ((AbstractArrow)projectile).pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            }
         }

         if (pShooter instanceof CrossbowAttackMob) {
            CrossbowAttackMob crossbowattackmob = (CrossbowAttackMob)pShooter;
            crossbowattackmob.shootCrossbowProjectile(crossbowattackmob.getTarget(), pCrossbowStack, projectile, pProjectileAngle);
         } else {
            Vec3 vec31 = pShooter.getUpVector(1.0F);
            Quaternionf quaternionf = (new Quaternionf()).setAngleAxis(pProjectileAngle * ((float)Math.PI / 180F), vec31.x, vec31.y, vec31.z);
            Vec3 vec3 = pShooter.getViewVector(1.0F);
            Vector3f vector3f = vec3.toVector3f().rotate(quaternionf);
            projectile.shoot(vector3f.x(), vector3f.y(), vector3f.z(), pVelocity, pInaccuracy);
         }

         pCrossbowStack.hurtAndBreak(flag ? 3 : 1, pShooter, (p_40858_) -> {
            p_40858_.broadcastBreakEvent(pHand);
         });
         pLevel.addFreshEntity(projectile);
         pLevel.playSound(null, pShooter.getX(), pShooter.getY(), pShooter.getZ(), ReduxSoundEvents.SUBZERO_CROSSBOW_SHOOT.get(), SoundSource.PLAYERS, 1.0F, pSoundPitch);
      }
   }

   private static AbstractArrow getArrow(Level pLevel, LivingEntity pLivingEntity, ItemStack pCrossbowStack, ItemStack pAmmoStack) {
      ArrowItem arrowitem = (ArrowItem)(pAmmoStack.getItem() instanceof ArrowItem ? pAmmoStack.getItem() : Items.ARROW);
      AbstractArrow abstractarrow = arrowitem.createArrow(pLevel, pAmmoStack, pLivingEntity);
      if (pLivingEntity instanceof Player) {
         abstractarrow.setCritArrow(true);
      }


      abstractarrow.setSoundEvent(ReduxSoundEvents.SUBZERO_CROSSBOW_HIT.get());
      abstractarrow.setShotFromCrossbow(true);
      int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PIERCING, pCrossbowStack);
      if (i > 0) {
         abstractarrow.setPierceLevel((byte)i);
      }

      SubzeroArrow.get(abstractarrow).ifPresent(subzeroArrow -> {
         subzeroArrow.setSubzeroArrow(true);
         int defaultTime = 200;
         subzeroArrow.setSlownessTime(defaultTime);
      });
      return abstractarrow;
   }

   public static void performShooting(Level pLevel, LivingEntity pShooter, InteractionHand pUsedHand, ItemStack pCrossbowStack, float pVelocity, float pInaccuracy) {
      if (pShooter instanceof Player player && net.minecraftforge.event.ForgeEventFactory.onArrowLoose(pCrossbowStack, pShooter.level(), player, 1, true) < 0) return;
      List<ItemStack> list = getChargedProjectiles(pCrossbowStack);
      float[] afloat = getShotPitches(pShooter.getRandom());

      for(int i = 0; i < list.size(); ++i) {
         ItemStack itemstack = list.get(i);
         boolean flag = pShooter instanceof Player && ((Player)pShooter).getAbilities().instabuild;
         if (!itemstack.isEmpty()) {
            if (i == 0) {
               shootProjectile(pLevel, pShooter, pUsedHand, pCrossbowStack, itemstack, afloat[i], flag, pVelocity, pInaccuracy, 0.0F);
            } else if (i == 1) {
               shootProjectile(pLevel, pShooter, pUsedHand, pCrossbowStack, itemstack, afloat[i], flag, pVelocity, pInaccuracy, -10.0F);
            } else if (i == 2) {
               shootProjectile(pLevel, pShooter, pUsedHand, pCrossbowStack, itemstack, afloat[i], flag, pVelocity, pInaccuracy, 10.0F);
            }
         }
      }

      onCrossbowShot(pLevel, pShooter, pCrossbowStack);
   }

   private static float[] getShotPitches(RandomSource pRandom) {
      boolean flag = pRandom.nextBoolean();
      return new float[]{1.0F, getRandomShotPitch(flag, pRandom), getRandomShotPitch(!flag, pRandom)};
   }

   private static float getRandomShotPitch(boolean pIsHighPitched, RandomSource pRandom) {
      float f = pIsHighPitched ? 0.63F : 0.43F;
      return 1.0F / (pRandom.nextFloat() * 0.5F + 1.8F) + f;
   }

   /**
    * Called after  to clear the charged projectile and to update the player advancements.
    */
   private static void onCrossbowShot(Level pLevel, LivingEntity pShooter, ItemStack pCrossbowStack) {
      if (pShooter instanceof ServerPlayer serverplayer) {
         if (!pLevel.isClientSide) {
            CriteriaTriggers.SHOT_CROSSBOW.trigger(serverplayer, pCrossbowStack);
         }

         serverplayer.awardStat(Stats.ITEM_USED.get(pCrossbowStack.getItem()));
      }

      clearChargedProjectiles(pCrossbowStack);
   }

   /**
    * Called as the item is being used by an entity.
    */
   public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pCount) {
      if (!pLevel.isClientSide) {
         int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, pStack);
         SoundEvent soundevent = this.getStartSound(i);
         SoundEvent soundevent1 = i == 0 ? ReduxSoundEvents.SUBZERO_CROSSBOW_LOADING_MIDDLE.get() : null;
         float f = (float)(pStack.getUseDuration() - pCount) / (float)getChargeDuration(pStack);
         if (f < 0.2F) {
            this.startSoundPlayed = false;
            this.midLoadSoundPlayed = false;
         }

         if (f >= 0.2F && !this.startSoundPlayed) {
            this.startSoundPlayed = true;
            pLevel.playSound(null, pLivingEntity.getX(), pLivingEntity.getY(), pLivingEntity.getZ(), soundevent, SoundSource.PLAYERS, 0.5F, 1.0F);
         }

         if (f >= 0.5F && soundevent1 != null && !this.midLoadSoundPlayed) {
            this.midLoadSoundPlayed = true;
            pLevel.playSound(null, pLivingEntity.getX(), pLivingEntity.getY(), pLivingEntity.getZ(), soundevent1, SoundSource.PLAYERS, 0.5F, 1.0F);
         }
      }
      super.onUseTick(pLevel, pLivingEntity, pStack, pCount);

   }

   /**
    * How long it takes to use or consume an item
    */
   public int getUseDuration(ItemStack pStack) {
      return getChargeDuration(pStack) + 3;
   }

   @Override
   public boolean isValidRepairItem(ItemStack pStack, ItemStack pRepairCandidate) {
      return pRepairCandidate.is(Blocks.BLUE_ICE.asItem());
   }

   /**
    * The time the crossbow must be used to reload it
    */
   public static int getChargeDuration(ItemStack pCrossbowStack) {
      int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, pCrossbowStack);
      return i == 0 ? 25 : 25 - 5 * i;
   }

   /**
    * Returns the action that specifies what animation to play when the item is being used.
    */
   public UseAnim getUseAnimation(ItemStack pStack) {
      return UseAnim.CROSSBOW;
   }

   private SoundEvent getStartSound(int pEnchantmentLevel) {
      switch (pEnchantmentLevel) {
         case 1:
            return ReduxSoundEvents.SUBZERO_CROSSBOW_QUICK_CHARGE_1.get();
         case 2:
            return ReduxSoundEvents.SUBZERO_CROSSBOW_QUICK_CHARGE_2.get();
         case 3:
            return ReduxSoundEvents.SUBZERO_CROSSBOW_QUICK_CHARGE_3.get();
         default:
            return ReduxSoundEvents.SUBZERO_CROSSBOW_LOADING_START.get();
      }
   }

   private static float getPowerForTime(int pUseTime, ItemStack pCrossbowStack) {
      float f = (float)pUseTime / (float)getChargeDuration(pCrossbowStack);
      if (f > 1.0F) {
         f = 1.0F;
      }

      return f;
   }

   /**
    * Allows items to add custom lines of information to the mouseover description.
    */
   public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
      List<ItemStack> list = getChargedProjectiles(pStack);
      if (isCharged(pStack) && !list.isEmpty()) {
         ItemStack itemstack = list.get(0);
         pTooltip.add(Component.translatable("item.minecraft.crossbow.projectile").append(CommonComponents.SPACE).append(itemstack.getDisplayName()));
         if (pFlag.isAdvanced() && itemstack.is(Items.FIREWORK_ROCKET)) {
            List<Component> list1 = Lists.newArrayList();
            Items.FIREWORK_ROCKET.appendHoverText(itemstack, pLevel, list1, pFlag);
            if (!list1.isEmpty()) {
               for(int i = 0; i < list1.size(); ++i) {
                  list1.set(i, Component.literal("  ").append(list1.get(i)).withStyle(ChatFormatting.GRAY));
               }

               pTooltip.addAll(list1);
            }
         }

      }
      if (pFlag.isCreative()) {
         pTooltip.add(AetherItems.GOLD_DUNGEON_TOOLTIP);
      }
   }

   /**
    * If this stack's item is a crossbow
    */
   public boolean useOnRelease(ItemStack pStack) {
      return pStack.is(this);
   }

   public int getDefaultProjectileRange() {
      return 8;
   }
}
