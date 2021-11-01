# Database_SQL
* SQL Database model.
* Trying to code SQL database with JAVA.
  You can CREATE and DELETE tables, ADD, EDIT, DELETE rows and columns and SEARCH in a field on a column or search a row completely. All by JSON recipes.

### Table of contents
* [Createing table](#create-table)
* [Adding new row](#add-new-row)
* [Search on a column](#search-on-a-column)
* [Search a row](#search-a-row)
* [Edit row](#edit-row)
* [Delete some field from a row](#delete-some-field-from-a-row)
* [Delete a row](#delete-a-row)
* [Delete table](#delete-table)


### Create Table
  Every table has a unique name and a prime colmun, so define table name and prime at first.
  For each column we have two parts, `column name` and `column type` . column type can be `string` , `integer` and `double` . If a column type is string, that's necessary to determine `character size` .
###### Syntax :
```JSON
{"action":"build",
        "table name":"t1",
        "prime":"c2",
        "addColumn":{"column name":"c1","column type":"string","character size":10},
        "addColumn":{"column name":"c2","column type":"integer","prime":"yes"},
        "addColumn":{"column name":"c3","column type":"double"}
        }
        done
```
  

### Add new row
  After createing the table you can add rows to table.
###### Syntax :
```JSON
{"action":"open",
        "table name":"t1",
        "addRow":{"column name":"c1","field":"arman","column name":"c2","field":44,"column name":"c3","field":88.2}
        }
        done
```

### Search on a column
  Prime column is unique for every rows in each table so we use it for `Search` , `Edit` and `Delete` rows.
  You can search one, tow or all column in one recipe.
###### Syntax :
```JSON
{"action":"open",
        "table name":"t1",
        "prime":44,
        "search":{"column name":"c1"}
        }
        done
```

### Search a row
  For searching all column of a row, you can use this syntax.
###### Syntax :
```JSON
{"action":"open",
        "table name":"t1",
        "prime":44,
        "search":"row"
        }
        done
```


### Edit row
  Some times you nead edit some information of a row.
###### Syntax :
```JSON
{"action":"open",
        "table name":"t1",
        "prime":44,
        "edit":{"column name":"c1","filed":"arman kazemi"}
        }
        done
```

### Delete some field from a row
  Now you can delete every column of a row that you want.
  _Notice:_ with deleteing prime key of a row, program go to fail.
###### Syntax :
```JSON
{"action":"open",
        "table name":"t1",
        "prime":44,
        "delete":{"column name":"c1","column name":"c3"}
        }
        done
```

### Delete a row
  Delete all field of a row.
###### Syntax :
```JSON
{"action":"open",
        "table name":"t1",
        "prime":44,
        "delete":"row"
        }
        done
```

### Delete table
  After all, delete table.
###### Syntax :
```JSON
{"action":"open",
        "table name":"t1",
        "delete":"table"
        }
        done
```
