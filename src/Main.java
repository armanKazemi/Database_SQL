

public class Main {
    public static void main (String[] args){
        MyConsole console = new MyConsole();
        console.start();
    }
}

//
//{"action":"build",
//        "table name":"t1",
//        "prime":"c2",
//        "addColumn":{"column name":"c1","column type":"string","character size":10},
//        "addColumn":{"column name":"c2","column type":"integer","prime":"yes"},
//        "addColumn":{"column name":"c3","column type":"double"}
//        }
//        done
//
//{"action":"open",
//        "table name":"t1",
//        "addRow":{"column name":"c1","field":"arman","column name":"c2","field":44,"column name":"c3","field":88.2}
//        }
//        done
//
//{"action":"open",
//        "table name":"t1",
//        "prime":44,
//        "search":{"column name":"c1"}
//        }
//        done
//
//{"action":"open",
//        "table name":"t1",
//        "prime":44,
//        "search":"row"
//        }
//        done
//
//{"action":"open",
//        "table name":"t1",
//        "prime":44,
//        "edit":{"column name":"c1","filed":"hossein"}
//        }
//        done
//
//{"action":"open",
//        "table name":"t1",
//        "prime":44,
//        "delete":{"column name":"c1","column name":"c3"}
//        }
//        done
//
//{"action":"open",
//        "table name":"t1",
//        "prime":44,
//        "delete":"row"
//        }
//        done
//
//{"action":"open",
//        "table name":"t1",
//        "delete":"table"
//        }
//        done
