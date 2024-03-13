package net.zepalesque.redux.item.weapons;

import com.aetherteam.aether.item.AetherItems;
import com.google.common.collect.Lists;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
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


import javax.annotation.Nullable;
import java.util.List;

public class SubzeroCrossbowItem extends CrossbowItem {
   /** Set to {@code true} when the crossbow is 20% charged. */
   private boolean startSoundPlayed = false;
   /** Set to {@code true} when the crossbow is 50% charged. */
   private boolean midLoadSoundPlayed = false;

   public SubzeroCrossbowItem(Item.Properties properties) {
      super(properties);
   }

   /**
    * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
    * .
    */
   @Override
   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack itemstack = player.getItemInHand(hand);
      if (isCharged(itemstack)) {
         performShooting(level, player, hand, itemstack, getShootingPower(itemstack), 1.0F);
         setCharged(itemstack, false);
         return InteractionResultHolder.consume(itemstack);
      } else if (!player.getProjectile(itemstack).isEmpty()) {
         if (!isCharged(itemstack)) {
            this.startSoundPlayed = false;
            this.midLoadSoundPlayed = false;
            player.startUsingItem(hand);
         }

         return InteractionResultHolder.consume(itemstack);
      } else {
         return InteractionResultHolder.fail(itemstack);
      }
   }

   private static float getShootingPower(ItemStack crossbowStack) {
      return containsChargedProjectile(crossbowStack, Items.FIREWORK_ROCKET) ? 1.6F : 3.15F;
   }

   /**
    * Called when the player stops using an Item (stops holding the right mouse button).
    */
   @Override

   public void releaseUsing(ItemStack stack, Level level, LivingEntity livingentity, int timeLeft) {
      int i = this.getUseDuration(stack) - timeLeft;
      float f = getPowerForTime(i, stack);
      if (f >= 1.0F && !isCharged(stack) && tryLoadProjectiles(livingentity, stack)) {
         setCharged(stack, true);
         SoundSource soundsource = livingentity instanceof Player ? SoundSource.PLAYERS : SoundSource.HOSTILE;
         level.playSound(null, livingentity.getX(), livingentity.getY(), livingentity.getZ(), ReduxSoundEvents.SUBZERO_CROSSBOW_LOADING_END.get(), soundsource, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F);
      }

   }

   private static boolean tryLoadProjectiles(LivingEntity shooter, ItemStack crossbowStack) {
      int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, crossbowStack);
      int j = i == 0 ? 1 : 3;
      boolean flag = shooter instanceof Player && ((Player)shooter).getAbilities().instabuild;
      ItemStack itemstack = shooter.getProjectile(crossbowStack);
      ItemStack itemstack1 = itemstack.copy();

      for(int k = 0; k < j; ++k) {
         if (k > 0) {
            itemstack = itemstack1.copy();
         }

         if (itemstack.isEmpty() && flag) {
            itemstack = new ItemStack(Items.ARROW);
            itemstack1 = itemstack.copy();
         }

         if (!loadProjectile(shooter, crossbowStack, itemstack, k > 0, flag)) {
            return false;
         }
      }

      return true;
   }

   private static boolean loadProjectile(LivingEntity shooter, ItemStack crossbowStack, ItemStack ammoStack, boolean hasAmmo, boolean isCreative) {
      if (ammoStack.isEmpty()) {
         return false;
      } else {
         boolean flag = isCreative && ammoStack.getItem() instanceof ArrowItem;
         ItemStack itemstack;
         if (!flag && !isCreative && !hasAmmo) {
            itemstack = ammoStack.split(1);
            if (ammoStack.isEmpty() && shooter instanceof Player) {
               ((Player)shooter).getInventory().removeItem(ammoStack);
            }
         } else {
            itemstack = ammoStack.copy();
         }

         addChargedProjectile(crossbowStack, itemstack);
         return true;
      }
   }

   public static boolean isCharged(ItemStack crossbowStack) {
      CompoundTag compoundtag = crossbowStack.getTag();
      return compoundtag != null && compoundtag.getBoolean("Charged");
   }

   public static void setCharged(ItemStack crossbowStack, boolean isCharged) {
      CompoundTag compoundtag = crossbowStack.getOrCreateTag();
      compoundtag.putBoolean("Charged", isCharged);
   }

   private static void addChargedProjectile(ItemStack crossbowStack, ItemStack ammoStack) {
      CompoundTag compoundtag = crossbowStack.getOrCreateTag();
      ListTag listtag;
      if (compoundtag.contains("ChargedProjectiles", 9)) {
         listtag = compoundtag.getList("ChargedProjectiles", 10);
      } else {
         listtag = new ListTag();
      }

      CompoundTag compoundtag1 = new CompoundTag();
      ammoStack.save(compoundtag1);
      listtag.add(compoundtag1);
      compoundtag.put("ChargedProjectiles", listtag);
   }

   private static List<ItemStack> getChargedProjectiles(ItemStack crossbowStack) {
      List<ItemStack> list = Lists.newArrayList();
      CompoundTag compoundtag = crossbowStack.getTag();
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



   private static void clearChargedProjectiles(ItemStack crossbowStack) {
      CompoundTag compoundtag = crossbowStack.getTag();
      if (compoundtag != null) {
         ListTag listtag = compoundtag.getList("ChargedProjectiles", 9);
         listtag.clear();
         compoundtag.put("ChargedProjectiles", listtag);
      }

   }

   private static void shootProjectile(Level level, LivingEntity shooter, InteractionHand hand, ItemStack crossbowStack, ItemStack ammoStack, float soundPitch, boolean isCreativeMode, float velocity, float inaccuracy, float projectileAngle) {
      if (!level.isClientSide) {
         boolean flag = ammoStack.is(Items.FIREWORK_ROCKET);
         Projectile projectile;
         if (flag) {
            projectile = new FireworkRocketEntity(level, ammoStack, shooter, shooter.getX(), shooter.getEyeY() - (double)0.15F, shooter.getZ(), true);
         } else {
            projectile = getArrow(level, shooter, crossbowStack, ammoStack);
            if (isCreativeMode || projectileAngle != 0.0F) {
               ((AbstractArrow)projectile).pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            }
         }

         if (shooter instanceof CrossbowAttackMob) {
            CrossbowAttackMob crossbowattackmob = (CrossbowAttackMob)shooter;
            crossbowattackmob.shootCrossbowProjectile(crossbowattackmob.getTarget(), crossbowStack, projectile, projectileAngle);
         } else {
            Vec3 vec31 = shooter.getUpVector(1.0F);
            Quaternion quaternion = new Quaternion(new Vector3f(vec31), projectileAngle, true);
            Vec3 vec3 = shooter.getViewVector(1.0F);
            Vector3f vector3f = new Vector3f(vec3);
            vector3f.transform(quaternion);
            projectile.shoot(vector3f.x(), vector3f.y(), vector3f.z(), velocity, inaccuracy);
         }

         crossbowStack.hurtAndBreak(flag ? 3 : 1, shooter, (p_40858_) -> {
            p_40858_.broadcastBreakEvent(hand);
         });
         level.addFreshEntity(projectile);
         level.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), ReduxSoundEvents.SUBZERO_CROSSBOW_SHOOT.get(), SoundSource.PLAYERS, 1.0F, soundPitch);
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
      if (pShooter instanceof Player player && net.minecraftforge.event.ForgeEventFactory.onArrowLoose(pCrossbowStack, pShooter.level, player, 1, true) < 0) return;
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
   public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int count) {
      if (!level.isClientSide) {
         int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
         SoundEvent soundevent = this.getStartSound(i);
         SoundEvent soundevent1 = i == 0 ? ReduxSoundEvents.SUBZERO_CROSSBOW_LOADING_MIDDLE.get() : null;
         float f = (float)(stack.getUseDuration() - count) / (float)getChargeDuration(stack);
         if (f < 0.2F) {
            this.startSoundPlayed = false;
            this.midLoadSoundPlayed = false;
         }

         if (f >= 0.2F && !this.startSoundPlayed) {
            this.startSoundPlayed = true;
            level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), soundevent, SoundSource.PLAYERS, 0.5F, 1.0F);
         }

         if (f >= 0.5F && soundevent1 != null && !this.midLoadSoundPlayed) {
            this.midLoadSoundPlayed = true;
            level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), soundevent1, SoundSource.PLAYERS, 0.5F, 1.0F);
         }
      }
      super.onUseTick(level, livingEntity, stack, count);

   }

   /**
    * How long it takes to use or consume an item
    */
   public int getUseDuration(ItemStack stack) {
      return getChargeDuration(stack) + 3;
   }

   @Override
   public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
      return repairCandidate.is(Blocks.BLUE_ICE.asItem());
   }

   /**
    * The time the crossbow must be used to reload it
    */
   public static int getChargeDuration(ItemStack crossbowStack) {
      int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, crossbowStack);
      return i == 0 ? 25 : 25 - 5 * i;
   }

   /**
    * Returns the action that specifies what animation to play when the item is being used.
    */
   public UseAnim getUseAnimation(ItemStack stack) {
      return UseAnim.CROSSBOW;
   }

   private SoundEvent getStartSound(int enchantmentLevel) {
      switch (enchantmentLevel) {
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

   private static float getPowerForTime(int useTime, ItemStack crossbowStack) {
      float f = (float)useTime / (float)getChargeDuration(crossbowStack);
      if (f > 1.0F) {
         f = 1.0F;
      }

      return f;
   }

   /**
    * Allows items to add custom lines of information to the mouseover description.
    */
   public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
      List<ItemStack> list = getChargedProjectiles(stack);
      if (isCharged(stack) && !list.isEmpty()) {
         ItemStack itemstack = list.get(0);
         tooltip.add(Component.translatable("item.minecraft.crossbow.projectile").append(" ").append(itemstack.getDisplayName()));
         if (flag.isAdvanced() && itemstack.is(Items.FIREWORK_ROCKET)) {
            List<Component> list1 = Lists.newArrayList();
            Items.FIREWORK_ROCKET.appendHoverText(itemstack, level, list1, flag);
            if (!list1.isEmpty()) {
               for(int i = 0; i < list1.size(); ++i) {
                  list1.set(i, Component.literal("  ").append(list1.get(i)).withStyle(ChatFormatting.GRAY));
               }

               tooltip.addAll(list1);
            }
         }

      }
      if (level != null && level.isClientSide() && Minecraft.getInstance().player != null && Minecraft.getInstance().player.isCreative()) {
         tooltip.add(AetherItems.GOLD_DUNGEON_TOOLTIP);
      }
   }

   /**
    * If this stack's item is a crossbow
    */
   public boolean useOnRelease(ItemStack stack) {
      return stack.is(this);
   }

   public int getDefaultProjectileRange() {
      return 8;
   }
}
