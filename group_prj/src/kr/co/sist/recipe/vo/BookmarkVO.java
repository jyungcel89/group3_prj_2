package kr.co.sist.recipe.vo;
public class BookmarkVO {
       private String id, menuName,img,menuType,MenuInfo,MenuPrice;
       
       public BookmarkVO() {
       }
       public BookmarkVO(String id, String menuName, String img, String menuType, String menuInfo, String menuPrice) {
              this.id = id;
              this.menuName = menuName;
              this.img = img;
              this.menuType = menuType;
              MenuInfo = menuInfo;
              MenuPrice = menuPrice;
       }
       public String getId() {
              return id;
       }
       public void setId(String id) {
              this.id = id;
       }
       public String getMenuName() {
              return menuName;
       }
       public void setMenuName(String menuName) {
              this.menuName = menuName;
       }
       public String getImg() {
              return img;
       }
       public void setImg(String img) {
              this.img = img;
       }
       public String getMenuType() {
              return menuType;
       }
       public void setMenuType(String menuType) {
              this.menuType = menuType;
       }
       public String getMenuInfo() {
              return MenuInfo;
       }
       public void setMenuInfo(String menuInfo) {
              MenuInfo = menuInfo;
       }
       public String getMenuPrice() {
              return MenuPrice;
       }
       public void setMenuPrice(String menuPrice) {
              MenuPrice = menuPrice;
       }
       
       
       
       
}
