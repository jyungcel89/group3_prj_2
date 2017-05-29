package kr.co.sist.recipe.vo;
public class BookmarkUpdateVO {
       private String id, menuName;
       public BookmarkUpdateVO() {
       }
       public BookmarkUpdateVO(String id, String menuName) {
              this.id = id;
              this.menuName = menuName;
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
       
       
}
