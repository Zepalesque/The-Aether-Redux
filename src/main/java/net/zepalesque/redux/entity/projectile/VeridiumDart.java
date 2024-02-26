package net.zepalesque.redux.entity.projectile;

import com.aetherteam.aether.entity.projectile.dart.AbstractDart;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.zepalesque.redux.entity.ReduxEntityTypes;
import net.zepalesque.redux.item.ReduxItems;

public class VeridiumDart extends AbstractDart {
    public VeridiumDart(EntityType<? extends VeridiumDart> type, Level level) {
        super(type, level, ReduxItems.VERIDIUM_DART);
        this.setBaseDamage(2.0);
    }

    public VeridiumDart(Level level) {
        super(ReduxEntityTypes.INFUSED_VERIDIUM_DART.get(), level, ReduxItems.VERIDIUM_DART);
        this.setBaseDamage(2.0);
    }
}
