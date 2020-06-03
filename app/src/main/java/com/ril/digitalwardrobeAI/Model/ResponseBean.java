package com.ril.digitalwardrobeAI.Model;


import java.util.ArrayList;

public class ResponseBean {


   /* public ResponseBean(ArrayList<Product> complementaryProduct, ArrayList<Product> Product, ArrayList<Product> similarStyleProduct,ArrayList<Product> similarColorProduct, ScannedProductBean msdTags) {
        this.complementaryProduct = complementaryProduct;
        this.Product = Product;
        this.similarStyleProduct = similarStyleProduct;
        this.similarColorProduct=similarColorProduct;
        this.msdTags = msdTags;
    }
*/

    public ResponseBean() {
    }

    public ArrayList<Product> getSimilarProduct() {
        return similarProducts;
    }

    public ArrayList<Product> getSimilarColorProduct() {
        return similarColorProducts;
    }

    public void setSimilarColorProduct(ArrayList<Product> similarColorProduct) {
        this.similarColorProducts = similarColorProduct;
    }

    public void setSimilarProduct(ArrayList<Product> Product) {
        this.similarProducts = Product;
    }

    public ArrayList<Product> getSimilarStyleProduct() {
        return similarStyleProducts;
    }

    public void setSimilarStyleProduct(ArrayList<Product> similarStyleProduct) {
        this.similarStyleProducts = similarStyleProduct;
    }


    public ScannedProductBean getMsdTags() {
        return msdTags;
    }

    public ArrayList<Product> getComplementaryProduct() {
        return complementaryProducts;
    }

    public void setComplementaryProduct(ArrayList<Product> complementaryProduct) {
        this.complementaryProducts = complementaryProduct;
    }

    public void setMsdTags(ScannedProductBean msdTags) {
        this.msdTags = msdTags;
    }

    ArrayList<Product> complementaryProducts;
    ArrayList<Product> similarProducts;

    public ArrayList<Product> getCatalogComplementaryProducts() {
        return catalogComplementaryProducts;
    }

    public void setCatalogComplementaryProducts(ArrayList<Product> catalogComplementaryProducts) {
        this.catalogComplementaryProducts = catalogComplementaryProducts;
    }

    ArrayList<Product> catalogComplementaryProducts;

    public ArrayList<Product> getCatalogSimilarProducts() {
        return catalogSimilarProducts;
    }

    public void setCatalogSimilarProducts(ArrayList<Product> catalogSimilarProducts) {
        this.catalogSimilarProducts = catalogSimilarProducts;
    }

    ArrayList<Product> catalogSimilarProducts;

    public ArrayList<String> getSortedColors() {
        return sortedColors;
    }

    public void setSortedColors(ArrayList<String> sortedColors) {
        this.sortedColors = sortedColors;
    }

    ArrayList<Product> similarStyleProducts;
    ArrayList<Product> similarColorProducts;
    ArrayList<String> sortedColors;

    public String getStaticAssetId() {
        return staticAssetId;
    }

    public void setStaticAssetId(String staticAssetId) {
        this.staticAssetId = staticAssetId;
    }

    String staticAssetId;

    public String getTextSuggestion() {
        return textSuggestion;
    }

    public void setTextSuggestion(String textSuggestion) {
        this.textSuggestion = textSuggestion;
    }

    String textSuggestion;

    ScannedProductBean msdTags;

}
