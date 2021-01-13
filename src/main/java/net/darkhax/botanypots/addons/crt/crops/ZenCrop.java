package net.darkhax.botanypots.addons.crt.crops;

import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.item.*;
import net.darkhax.bookshelf.block.*;
import net.darkhax.botanypots.crop.*;
import net.minecraft.block.*;
import net.minecraft.item.crafting.*;
import net.minecraft.util.*;
import org.openzen.zencode.java.*;

import java.util.*;

@ZenRegister
@ZenCodeType.Name("mods.botanypots.ZenCrop")
public class ZenCrop {
    
    private final CropInfo internal;
    
    public ZenCrop(String id, IIngredient seed, BlockState[] display, int ticks, String[] categories, int lightLevel) {
        
        this(new CropInfo(ResourceLocation.tryCreate(id), seed.asVanillaIngredient(), new HashSet<>(Arrays.asList(categories)), ticks, new ArrayList<>(), getBlockStates(display), Optional.of(lightLevel)));
    }
    
    public ZenCrop(String id, IIngredient seed, BlockState[] display, int ticks, String[] categories) {
        
        this(new CropInfo(ResourceLocation.tryCreate(id), seed.asVanillaIngredient(), new HashSet<>(Arrays.asList(categories)), ticks, new ArrayList<>(), getBlockStates(display), Optional.empty()));
    }
    
    public ZenCrop(CropInfo crop) {
        
        this.internal = crop;
    }
    
    @ZenCodeType.Method
    public ZenCrop addCategory (String category) {
        
        this.internal.getSoilCategories().add(category);
        return this;
    }
    
    @ZenCodeType.Method
    public ZenCrop removeCategory (String category) {
        
        this.internal.getSoilCategories().remove(category);
        return this;
    }
    
    @ZenCodeType.Method
    public ZenCrop clearCategories () {
        
        this.internal.getSoilCategories().clear();
        return this;
    }
    
    @ZenCodeType.Method
    public ZenCrop addDrop (IItemStack item, float chance) {
        
        return this.addDrop(item, chance, 1);
    }
    
    @ZenCodeType.Method
    public ZenCrop addDrop (IItemStack item, float chance, int rolls) {
        
        return this.addDrop(item, chance, rolls, rolls);
    }
    
    @ZenCodeType.Method
    public ZenCrop addDrop (IItemStack item, float chance, int min, int max) {
        
        this.internal.getResults().add(new HarvestEntry(chance, item.getInternal(), min, max));
        return this;
    }
    
    @ZenCodeType.Method
    public ZenCrop clearDrops () {
        
        this.internal.getResults().clear();
        return this;
    }
    
    @ZenCodeType.Method
    public ZenCrop removeDrop (IIngredient toRemove) {
        
        final Ingredient ingredient = toRemove.asVanillaIngredient();
        this.internal.getResults().removeIf(drop -> ingredient.test(drop.getItem()));
        return this;
    }
    
    @ZenCodeType.Method
    public ZenCrop setGrowthTicks (int ticks) {
        
        this.internal.setGrowthTicks(ticks);
        return this;
    }
    
    @ZenCodeType.Method
    public ZenCrop setSeed (IIngredient seed) {
        
        this.internal.setSeed(seed.asVanillaIngredient());
        return this;
    }
    
    @ZenCodeType.Method
    public ZenCrop setDisplay (BlockState state) {
        
        this.internal.setDisplayBlock(getBlockStates(state));
        return this;
    }
    
    @ZenCodeType.Method
    public ZenCrop setDisplay (BlockState[] states) {
        
        this.internal.setDisplayBlock(getBlockStates(states));
        return this;
    }
    
    @ZenCodeType.Method
    public ZenCrop setLightLevel (int lightLevel) {
        
        this.internal.setLightLevel(lightLevel);
        return this;
    }
    
    public CropInfo getInternal () {
        
        return this.internal;
    }
    
    public static DisplayableBlockState[] getBlockStates (BlockState... states) {
        return Arrays.stream(states).map(DisplayableBlockState::new).toArray(DisplayableBlockState[]::new);
    }
}