package net.zepalesque.redux.item.food;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.zepalesque.redux.Redux;

public class SkyrootBowlFoodItem extends Item {
   public SkyrootBowlFoodItem(Item.Properties pProperties) {
      super(pProperties);
   }

   /**
    * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
    * the Item before the action is complete.
    */
   public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
      ItemStack itemstack = super.finishUsingItem(pStack, pLevel, pEntityLiving);
      return pEntityLiving instanceof Player && ((Player)pEntityLiving).getAbilities().instabuild ? itemstack : new ItemStack(getBowl());
   }

   public static Item getBowl()
   {
      // TODO: Investigate why the skyroot bowl does not appear to exist anymore
      if (Redux.aetherGenesisCompat())
      {
//         return GenesisItems.bowl.get();
      }
         return Items.BOWL;
   }
}