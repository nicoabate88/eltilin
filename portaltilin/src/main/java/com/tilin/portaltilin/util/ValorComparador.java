
package com.tilin.portaltilin.util;

import com.tilin.portaltilin.entidades.Valor;
import java.util.Comparator;


public class ValorComparador {
    
      public static Comparator<Valor> ordenarIdDesc = new Comparator<Valor>() {
        @Override
        public int compare(Valor v1, Valor v2) {
            return v2.getId().compareTo(v1.getId());
        }
    };
    
}
