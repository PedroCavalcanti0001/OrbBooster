package com.coding.sirjavlux.orbbooster.orbs;

import org.bukkit.Material;

import java.util.List;

public class Orb {

    private List<String> lore, destLore, loreBox;
    private String name, displayName, destDisplayName, displayNameBox;
    private Material mat, destMat, matBox;
    private double boostMin, boostMax, chanceMin, chanceMax, chanceBreak;
    private int craftAmount;

    public Orb(List<String> lore, String name, String displayName, Material mat, List<String> destLore, String destDisplayName, Material destMat, List<String> loreBox, String displayNameBox, Material matBox, double boostMin, double boostMax, double chanceMin, double chanceMax, double chanceBreak, int craftAmount) {
        this.loreBox = loreBox;
        this.displayNameBox = displayNameBox;
        this.lore = lore;
        this.name = name;
        this.displayName = displayName;
        this.mat = mat;
        this.matBox = matBox;
        this.destMat = destMat;
        this.destDisplayName = destDisplayName;
        this.destLore = destLore;
        this.boostMin = boostMin;
        this.boostMax = boostMax;
        this.chanceMin = chanceMin;
        this.chanceMax = chanceMax;
        this.chanceBreak = chanceBreak;
        this.craftAmount = craftAmount;
    }

    public List<String> getLore() {
        return lore;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Material getMaterial() {
        return mat;
    }

    public List<String> getDestLore() {
        return destLore;
    }

    public String getDestDisplayName() {
        return destDisplayName;
    }

    public Material getDestMaterial() {
        return destMat;
    }

    public List<String> getLoreBox() {
        return loreBox;
    }

    public String getDisplayNameBox() {
        return displayNameBox;
    }

    public Material getMaterialBox() {
        return matBox;
    }

    public double getBoostMin() {
        return boostMin;
    }

    public double getBoostMax() {
        return boostMax;
    }

    public double getChanceMin() {
        return chanceMin;
    }

    public double getChanceMax() {
        return chanceMax;
    }

    public double getChanceBreak() {
        return chanceBreak;
    }

    public int getCraftAmount() {
        return craftAmount;
    }
}