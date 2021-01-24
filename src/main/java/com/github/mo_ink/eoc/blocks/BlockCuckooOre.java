package com.github.mo_ink.eoc.blocks;

import com.github.mo_ink.eoc.EOC;
import com.github.mo_ink.eoc.EOCTab;
import com.github.mo_ink.eoc.handler.ItemHandler;
import net.minecraft.block.BlockOre;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

public class BlockCuckooOre extends BlockOre {
    public BlockCuckooOre() {
        super(MapColor.STONE);
        String name = "cuckoo_ore";
        this.setSoundType(SoundType.STONE);
        this.setHardness(3F);
        this.setHarvestLevel("pickaxe", 2);
        this.setCreativeTab(EOCTab.EOC_TAB);
        this.setRegistryName(name);
        this.setUnlocalizedName(EOC.MODID + "." + name);
    }

    // SRG func_180660_a，用于决定掉落的物品种类
    @Nonnull
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ItemHandler.ITEM_CUCKOO_INGOT;
    }

    // SRG func_149745_a，用于决定掉落的物品数量
    @Override
    public int quantityDropped(Random random) {
        return 1;
    }

    // SRG func_149679_a，用于决定受时运影响时掉落的物品数量
    @Override
    public int quantityDroppedWithBonus(int fortune, Random random) {
        if (fortune > 0) {
            int bonusFactor = Math.max(random.nextInt(fortune + 2) - 1, 0);
            return this.quantityDropped(random) * (bonusFactor + 1);
        } else {
            return this.quantityDropped(random);
        }
    }

    // Forge 的 patch，用于决定掉落的经验数量（参考原版煤炭、红石、青金石、钻石、绿宝石、下界石英）
    @Override
    public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
        Random random = world instanceof World ? ((World) world).rand : new Random();
        return MathHelper.getInt(random, 4, 10);
    }

    // Forge 的 patch，取代 getItem (func_185473_a)，用于创造模式下鼠标中键选取方块的功能。
    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(this);
    }

    @Override // Forge patch 的方法
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        // 这个方法决定了精准采集有没有效果。
        return true;
    }

    @Override // func_180643_i
    public ItemStack getSilkTouchDrop(IBlockState state) {
        // 而这个方法决定了精准采集会掉什么
        return new ItemStack(this);
    }
}
