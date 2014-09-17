/**
 * Can generate SQL statements.
 *
 * <p>
 * General notes:
 * <p>
 * The columnSequence: These arguments are usually the sequence of the columns
 * such as this example:
 * <pre>
 *   String colSeq = "id, name, price";
 * </pre>
 *
 * The columnList: These, the arrays are list of all the column names, the code
 * generates the sequence out of it. Example:
 * <pre>
 *   String[] colList = {
 *       "id",
 *       "name",
 *       "price"
 *   };
 * </pre>
 *
 * The conditions: These strings are the complete SQL conditions, example:
 * <pre>
 *   String cond = "id = 1";
 *   String cond2 = "id = 1 AND name like '%'";
 *   String cond3 = "id = ?"
 * </pre>
 *
 * Conditions might be array in this case consider it as list of your
 * conditions. Example:
 * <pre>
 *   String[] cond = new String[] {
 *       "id = 1"
 *   };
 *   String[] cond2 = new String[] {
 *       "id = 1",
 *       "AND",
 *       "name like '%'"
 *   };
 *   String[] cond3 = new String[] {
 *       "id = ?"
 *   };
 * </pre>
 *
 * Licensed under MIT LICENSE.
 * Copyright © Rawand Fatih.
 * @author Rawand Fatih (rawandnf@gmail.com)
 */
public class SQLGenerator {

  /**
   * This method generates a SQL Select statement. Table name is required. If
   * columnSquence was null, it will query for * . If conditions was null it
   * will query with no conditions. {@link IllegalArgumentException} is thrown
   * if no table name is specified.
   * <p>
   * Example:
   * <pre>
   *  String tableName = "items";
   *  String columns = "id, name";
   *  String condition = "id = 1";
   *
   *  String selectStatement = generateSelectString(tableName,
   *      columns, condition);
   * </pre>
   * <p>
   * In this example you would get a string like this:
   * <p>
   * SELECT id, name FROM items WHERE id = 1;
   *
   * @param columnSquence The sequence of the columns, null for * (all columns).
   * @param tableName The table name.
   * @param conditions The conditions, null for no conditions.
   * @return The generated select SQL statement.
   */
  public static String generateSelectString(String tableName,
      String columnSquence, String conditions) {

    String sql = "SELECT ";
    if (columnSquence != null && !"".equals(columnSquence)) {
      sql += columnSquence;
    } else {
      sql += "*";
    }

    if (tableName != null && !"".equals(tableName)) {
      sql += " FROM " + tableName;
    } else {
      throw new IllegalArgumentException("Table name should be specified");
    }

    if (conditions != null && !"".equals(conditions)) {
      sql += " WHERE " + conditions;
    }

    return sql;
  }


  /**
   * This method generates a SQL Select statement with no conditions. Table name
   * is required. If columnSquence was null, it will query for * .
   * {@link IllegalArgumentException} is thrown if no table name is specified.
   *
   * @param columnSquence The sequence of the columns, null for * (all columns).
   * @param tableName The table name.
   * @return The generated select SQL statement.
   * @see SQLGenerator#generateSelectString(java.lang.String, java.lang.String,
   * java.lang.String)
   */
  public static String generateSelectString(String tableName,
      String columnSquence) {
    return generateSelectString(tableName, columnSquence, "");
  }


  /**
   * This method generates a SQL Select statement with all columns '*' and no
   * conditions. Table name is required. {@link IllegalArgumentException} is
   * thrown if no table name is specified.
   *
   * @param tableName The table name.
   * @return The generated select SQL statement.
   * @see SQLGenerator#generateSelectString(java.lang.String, java.lang.String,
   * java.lang.String)
   */
  public static String generateSelectString(String tableName) {
    return generateSelectString(tableName, "", "");
  }


  /**
   * This method generates a SQL Select statement. Table name is required. It
   * generates the column name sequence out of the given column list. If
   * conditions was null it will query with no conditions.
   * {@link IllegalArgumentException} is thrown if no table name is specified.
   * <p>
   * Examples:
   * <pre>
   *  String tableName = "items";
   *  String columns = "id, name";
   *  String condition = "id = 1";
   *
   *  String selectStatement = generateSelectString(tableName,
   *      columns, condition);
   * </pre>
   * <p>
   * In this example you would get a string like this:
   * <p>
   * SELECT id, name FROM items WHERE id = 1;
   *
   * @param columnList The sequence of the columns, null for * (all columns).
   * @param tableName The table name.
   * @param conditions The conditions, null for no conditions.
   * @return The generated select SQL statement.
   * @see SQLGenerator#generateSelectString(java.lang.String, java.lang.String,
   * java.lang.String)
   */
  public static String generateSelectString(String tableName,
      String[] columnList, String conditions) {

    return generateSelectString(tableName,
        arrayToSeparatedWordSequence(columnList), conditions);
  }


  /**
   * This method generates a SQL Select statement with no conditions. Table name
   * is required. It gets the column name from the given list.
   * {@link IllegalArgumentException} is thrown if no table name is specified.
   *
   * @param columnList The list of the columns.
   * @param tableName The table name.
   * @return The generated select SQL statement.
   * @see SQLGenerator#generateSelectString(java.lang.String, java.lang.String,
   * java.lang.String)
   */
  public static String generateSelectString(String tableName,
      String[] columnList) {
    return generateSelectString(tableName,
        arrayToSeparatedWordSequence(columnList), "");
  }


  /**
   * This method generates a SQL Select statement. Table name is required. It
   * generates the column name sequence out of the given column list. It
   * generates the condition sequence out of the condition list. See example.
   * {@link IllegalArgumentException} is thrown if no table name is specified.
   * <p>
   * Examples:
   * <pre>
   *  String tableName = "items";
   *  String columns = "id, name, price";
   *  String[] conditions = new String[]{
   *      "id = 1",
   *      "AND",
   *      "price = 20"
   *  };
   *
   *  String selectStatement = generateSelectString(tableName,
   *      columns, conditions);
   * </pre>
   * <p>
   * In this example you would get a string like this:
   * <p>
   * SELECT id, name, price FROM items WHERE id = 1 AND price = 20;
   *
   * @param columnList The list of the columns.
   * @param tableName The table name.
   * @param conditionsList The conditions list.
   * @return The generated select SQL statement.
   * @see SQLGenerator#generateSelectString(java.lang.String, java.lang.String,
   * java.lang.String)
   */
  public static String generateSelectString(String tableName,
      String[] columnList, String[] conditionsList) {

    return generateSelectString(tableName,
        arrayToSeparatedWordSequence(columnList),
        arrayToSeparatedWordSequence(conditionsList, false, " "));
  }


  /**
   * This method generates a SQL Select statement. Table name is required. If
   * columnSquence was null, it will query for * . It generates the condition
   * sequence out of the condition list. See example.
   * {@link IllegalArgumentException} is thrown if no table name is specified.
   * <p>
   * Examples:
   * <pre>
   *  String tableName = "items";
   *  String columns = "id, name, price";
   *  String[] conditions = new String[]{
   *      "id = 1",
   *      "AND",
   *      "price = 20"
   *  };
   *
   *  String selectStatement = generateSelectString(tableName,
   *      columns, conditions);
   * </pre>
   * <p>
   * In this example you would get a string like this:
   * <p>
   * SELECT id, name, price FROM items WHERE id = 1 AND price = 20;
   *
   * @param columnSquence The sequence of the columns, null for * (all columns).
   * @param tableName The table name.
   * @param conditionsList The conditions list.
   * @return The generated select SQL statement.
   * @see SQLGenerator#generateSelectString(java.lang.String, java.lang.String,
   * java.lang.String)
   */
  public static String generateSelectString(String tableName,
      String columnSquence, String[] conditionsList) {

    return generateSelectString(tableName, columnSquence,
        arrayToSeparatedWordSequence(conditionsList, false, " "));
  }


  /**
   * This method generates a SQL Update Statement. The update column sequence is
   * the column name and its new value. Table name and update column sequence
   * are required otherwise you'll get {@link IllegalArgumentException}.
   * <p>
   * Example:
   * <pre>
   *  String tableName = "items";
   *  String updateColumnSquence = "price = 20, name = 10";
   *  String condition = "id = 1";
   *
   *  String selectStatement = generateSelectString(tableName,
   *      updateColumnSquence, condition);
   * </pre>
   * <p>
   * In this example you would get a string like this:
   * <p>
   * UPDATE items SET price = 20, name = 10 WHERE id = 1
   *
   * @param tableName The name of the table.
   * @param updateColumnSquence The column name and new values.
   * @param conditions The conditions for query, null or "" for none.
   * @return The SQL update statement.
   */
  public static String generateUpdateString(String tableName,
      String updateColumnSquence, String conditions) {
    String sql = "UPDATE ";

    if (tableName != null && !"".equals(tableName)) {
      sql += tableName + " ";
    } else {
      throw new IllegalArgumentException("Table name should be specified");
    }

    if (updateColumnSquence != null && !"".equals(updateColumnSquence)) {
      sql += "SET " + updateColumnSquence;
    } else {
      throw new IllegalArgumentException("Table name should be specified");
    }

    if (conditions != null && !"".equals(conditions)) {
      sql += " WHERE " + conditions;
    }

    return sql;
  }


  /**
   * This method generates a SQL Update Statement with no condition.
   *
   * @param tableName The name of the table.
   * @param updateColumnSquence The column name and new values.
   * @return The SQL update statement.
   * @see SQLGenerator#generateUpdateString(java.lang.String, java.lang.String,
   * java.lang.String)
   */
  public static String generateUpdateString(String tableName,
      String updateColumnSquence) {
    return generateUpdateString(tableName, updateColumnSquence, "");
  }


  /**
   * This method generates a SQL Update Statement. It sets the values to ? so
   * that you can inject values via your statement.
   *
   * @param tableName The name of the table.
   * @param updateColumnList The list of columns.
   * @param conditions The conditions for query, null or "" for none.
   * @return The SQL update statement.
   * @see SQLGenerator#generateUpdateString(java.lang.String, java.lang.String,
   * java.lang.String)
   */
  public static String generateUpdateString(String tableName,
      String[] updateColumnList, String conditions) {
    return generateUpdateString(tableName,
        columnListToUpdateSetParams(updateColumnList), conditions);
  }


  /**
   * This method generates a SQL Update Statement. It sets the values to ? so
   * that you can inject values via your statement.
   *
   * @param tableName The name of the table.
   * @param updateColumnList The list of columns.
   * @param conditionsList The condition list.
   * @return The SQL update statement.
   * @see SQLGenerator#generateUpdateString(java.lang.String, java.lang.String,
   * java.lang.String)
   */
  public static String generateUpdateString(String tableName,
      String[] updateColumnList, String[] conditionsList) {

    return generateUpdateString(tableName,
        columnListToUpdateSetParams(updateColumnList),
        arrayToSeparatedWordSequence(conditionsList, false, " "));
  }


  /**
   * This method generates a SQL Update Statement with no condition. It sets the
   * values to ? so that you can inject values via your statement.
   *
   * @param tableName The name of the table.
   * @param updateColumnList The list of columns.
   * @return The SQL update statement.
   * @see SQLGenerator#generateUpdateString(java.lang.String, java.lang.String,
   * java.lang.String)
   */
  public static String generateUpdateString(String tableName,
      String[] updateColumnList) {
    return generateUpdateString(tableName,
        columnListToUpdateSetParams(updateColumnList), "");
  }


  /**
   * This method generates a SQL Update Statement. The update column sequence is
   * the column name and its new value. Table name and update column sequence
   * are required otherwise you'll get {@link IllegalArgumentException}.
   * <p>
   * Example:
   * <pre>
   *  String tableName = "items";
   *  String updateColumnSquence = "price = 40";
   *  String[] condition = {
   *      "id = 1",
   *      "AND",
   *      "price = 20"
   *  };
   *
   *  String selectStatement = generateSelectString(tableName,
   *      updateColumnSquence, condition);
   * </pre>
   * <p>
   * In this example you would get a string like this:
   * <p>
   * UPDATE items SET price = 40 WHERE WHERE id = 1 AND price = 20
   *
   * @param tableName The name of the table.
   * @param updateColumnSquence The column name and new values.
   * @param conditionsList The condition list.
   * @return The SQL update statement.
   */
  public static String generateUpdateString(String tableName,
      String updateColumnSquence, String[] conditionsList) {
    return generateUpdateString(tableName, updateColumnSquence,
        arrayToSeparatedWordSequence(conditionsList, false, " "));
  }


  /**
   * Generates a SQL Delete Statement. If no conditions required leave
   * conditions as an empty String.
   * <p>
   * Example:
   * <pre>
   *   String tableName = "items"
   *   String conditions = "id = 1";
   *   String sql = generateDeleteString(tableName, conditions);
   *   // DELETE FROM items WHERE id = 1
   * </pre>
   *
   * @param tableName The table name.
   * @param conditions Conditions sting as described in class notes.
   * @return The SQL delete statement.
   */
  public static String generateDeleteString(String tableName,
      String conditions) {
    String sql = "DELETE FROM ";

    if (tableName != null && !"".equals(tableName)) {
      sql += tableName;
    } else {
      throw new IllegalArgumentException("Table name should be specified");
    }

    if (conditions != null && !"".equals(conditions)) {
      sql += " WHERE " + conditions;
    }

    return sql;
  }


  /**
   * Generates a SQL Delete Statement.
   * <p>
   * Example:
   * <pre>
   *   String tableName = "items"
   *   String[] conditions = new String[] {
   *       "id = 1",
   *       "AND",
   *       "price > 20"
   *   };
   *   String sql = generateDeleteString(tableName, conditions);
   *   // DELETE FROM items WHERE id = 1 AND price > 20
   * </pre>
   *
   * @param tableName The table name.
   * @param conditionsList Conditions list.
   * @return The SQL delete statement.
   * @see SQLGenerator#generateDeleteString(java.lang.String, java.lang.String)
   */
  public static String generateDeleteString(String tableName,
      String[] conditionsList) {
    return generateDeleteString(tableName,
        arrayToSeparatedWordSequence(conditionsList, false, " "));
  }


  /**
   * Generates SQL Delete Statement with no conditions.
   *
   * @param tableName The table name.
   * @return The SQL delete statement.
   * @see SQLGenerator#generateDeleteString(java.lang.String, java.lang.String)
   */
  public static String generateDeleteString(String tableName) {
    return generateDeleteString(tableName, "");
  }


  /**
   * Generates an SQL Insert statement. Table name is required and the column
   * list should at least have one element. If one of the requirements fail you
   * will get {@link IllegalArgumentException}.
   * <p>
   * Example:
   * <pre>
   *   String tableName = "items";
   *   String[] columns = new String[]{"id", "name", "price"};
   *   String sql = generateInsertString(tableName, columns);
   *   // INSERT INTO items (id, name, price) (?, ?, ?)
   * </pre>
   *
   * @param tableName The table name.
   * @param columnsList List of the columns, at least one.
   * @return The generated SQL Insert statement.
   */
  public static String generateInsertString(String tableName,
      String[] columnsList) {
    String sql = "INSERT INTO ";
    if (tableName != null && !"".equals(tableName)) {
      sql += tableName;
    } else {
      throw new IllegalArgumentException("Table name should be specified");
    }

    if (columnsList.length <= 0) {
      throw new IllegalArgumentException("Columns should at least be one");
    }

    sql += " ";
    String colmnSequence = arrayToSeparatedWordSequence(columnsList);
    if (colmnSequence != null && !"".equals(colmnSequence)) {
      sql += "(" + colmnSequence + ") ";
    }

    String vals = "VALUES (";
    for (int i = 0; i < columnsList.length; i++) {
      vals += "?";
      if (i < columnsList.length - 1) {
        vals += ", ";
      }
    }
    vals += ")";
    sql += vals;

    return sql;
  }


  /**
   * Creates a separated word sequence out of a String array. You can choose a
   * separator of your own or gets ',' by default. Also you have condition for
   * adding space after the separator.
   * <p>
   * From an input as
   * <pre>["Hello", "Yellow", "Jello"]</pre> you would get:
   * <pre>Hello, Yellow, Jello</pre>
   *
   * @param wordArray The array of words.
   * @param hasSpaceAfterSeparator Condition for space after the separator.
   * @param separator The separator for word separation. Default is ','.
   * @return Separated word sequence.
   */
  public static String arrayToSeparatedWordSequence(String[] wordArray,
      boolean hasSpaceAfterSeparator, String separator) {

    String wordSequence = "";
    separator = (separator != null && !"".equals(separator))
        ? separator : ",";
    separator = (hasSpaceAfterSeparator) ? separator + " " : separator;

    for (int i = 0; i < wordArray.length; i++) {
      wordSequence += wordArray[i];
      if (i != wordArray.length - 1) {
        wordSequence += separator;
      }
    }

    return wordSequence;
  }


  /**
   * Creates a separated word sequence out of a String array. You can choose a
   * separator of your own or gets ',' by default. It adds a space after the
   * separator.
   * <p>
   * From an input as
   * <pre>["Hello", "Yellow", "Jello"]</pre> you would get:
   * <pre>Hello, Yellow, Jello</pre>
   *
   * @param wordArray The array of words.
   * @return Separated word sequence.
   */
  public static String arrayToSeparatedWordSequence(String[] wordArray) {
    return arrayToSeparatedWordSequence(wordArray, true, null);
  }


  /**
   * Generates set parameters for update query from a column list. It sets the
   * value of the columns to ? so that values can be injected to you SQL
   * statement.
   * <p>
   * Example:
   * <pre>
   *   String[] columns = new String[] {
   *       "id",
   *       "name",
   *       "price"
   *   };
   *   String setParams = columnListToUpdateSetParams(columns);
   *   System.out.println(setParams);
   *
   *   // OUTPUT
   *   // id = ?, name = ?, price = ?
   * </pre>
   *
   * @param columnList The list of columns.
   * @return Columns with values set to ?.
   */
  public static String columnListToUpdateSetParams(String[] columnList) {

    String wordSequence = "";
    String value = " = ?";

    for (int i = 0; i < columnList.length; i++) {
      wordSequence += columnList[i];
      wordSequence += value;
      if (i != columnList.length - 1) {
        wordSequence += ", ";
      }
    }

    return wordSequence;
  }

}
