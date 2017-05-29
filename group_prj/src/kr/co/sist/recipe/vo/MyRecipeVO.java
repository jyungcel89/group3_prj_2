package kr.co.sist.recipe.vo;
/**
 * 마이페이지 나의 레시피VO
 * @author user
 */
public class MyRecipeVO {
 private String menuName,menuImg,menuType,menuInfo, menuPrice,flag;
public MyRecipeVO() {
       
}
public MyRecipeVO(String menuName, String menuImg, String menuType, String menuInfo, String menuPrice,
              String flag) {
       this.menuName = menuName;
       this.menuImg = menuImg;
       this.menuType = menuType;
       this.menuInfo = menuInfo;
       this.menuPrice = menuPrice;
       this.flag = flag;
}
public String getMenuName() {
       return menuName;
}
public void setMenuName(String menuName) {
       this.menuName = menuName;
}
public String getMenuImg() {
       return menuImg;
}
public void setMenuImg(String menuImg) {
       this.menuImg = menuImg;
}
public String getMenuType() {
       return menuType;
}
public void setMenuType(String menuType) {
       this.menuType = menuType;
}
public String getMenuInfo() {
       return menuInfo;
}
public void setMenuInfo(String menuInfo) {
       this.menuInfo = menuInfo;
}
public String getMenuPrice() {
       return menuPrice;
}
public void setMenuPrice(String menuPrice) {
       this.menuPrice = menuPrice;
}
public String getFlag() {
       return flag;
}
public void setFlag(String flag) {
       this.flag = flag;
}
@Override
public String toString() {
       return "MyRecipeVO [menuName=" + menuName + ", menuImg=" + menuImg + ", menuType=" + menuType + ", menuInfo="
                     + menuInfo + ", menuPrice=" + menuPrice + ", flag=" + flag + "]";
}
}
