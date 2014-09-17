public class Test {

  public static void main(String[] args) {
//    String tableName = "items";
//    String[] columns = new String[]{"id", "name", "price"};
//    String[] conditions = new String[]{
//      "id = 1",
//      "AND",
//      "price = 20"
//    };
//    String sql = generateUpdateString(tableName, "price = ?", conditions);
//    System.out.println(sql);
//    // SELECT id, name, price FROM items WHERE id = 1 AND price = 20
//
//    System.out.println(generateDeleteString(tableName, "id = 1"));
//    System.out.println(generateDeleteString(tableName));
//    System.out.println(generateDeleteString(tableName, conditions));
//
//    System.out.println(generateInsertString("items", columns));

    String[] columns = new String[]{"id", "name", "price"};
    String sql = SQLGenerator.generateInsertString("items", columns);
    System.out.println(sql);
  }

}
