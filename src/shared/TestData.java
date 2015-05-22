package shared;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import unifiedshoppingexperience.Product;
import utility.CaseInsensitiveKeyMap;

/**
 * Provides test data that was needed before we had a database.
 *
 * @author Gruppe12
 */
public class TestData
{
    public static final Product p1, p2, p3, p4, p5;
    public static final String[] testProducts;
    private static final Map<String, Product> productMap;

    static
    {
        p1 = new Product("NV970", new BigDecimal(300.0), "Grafikkort", "Nvidia Geforce GTX 970");
        p2 = new Product("MSI970", new BigDecimal(150.0), "Grafikkort", "MSI Geforce GTX 970");
        p3 = new Product("NV660", new BigDecimal(100.0), "Grafikkort", "Nvidia Geforce GTX 660");
        p4 = new Product("I7-4770K", new BigDecimal(299.99), "Processorer", "Intel Core I7 4770K");
        p5 = new Product("I7-4670K", new BigDecimal(129.99), "Processorer", "Intel Core I5 4670K");

        testProducts = new String[]
        {
            "FD8350FRW8KHK, AMD FX-8350, Processorer, 179.99",
            "FD9590FHW8KHK, AMD FX-9590, Processorer, 239.99",
            "FD6300WMW6KHK, AMD FX-6300, Processorer, 109.99",
            "FD4100WMW4KGU, AMD FX-4100, Processorer, 79.99",
            "FD8120FRW8KGU, AMD FX-8120, Processorer, 100",
            "AD740XOKA44HJ, AMD 740, Processorer, 72.85",
            "AD750KWOA44HJ, AMD 750k, Processorer, 76.99",
            "BX80646I74770K, Intel i7-4770K, Processorer, 335.99",
            "BX80646I74790K, Intel i7-4790K, Processorer, 339.99",
            "BX80646I54670K, Intel i5-4670K, Processorer, 234.99",
            "BX80646I54690K, Intel i5-4690K, Processorer, 239.99",
            "BX80637I53570K, Intel i5-3570K, Processorer, 239.99",
            "N82E16813131876, Asus ROG Crosshair V Formula-Z, Bundkort, 229.99",
            "N82E16813131883, Asus F2A85-M PRO, Bundkort, 94.99",
            "N82E16813131975, Asus ROG Maximus VI Gene, Bundkort, 159.99",
            "N82E16813131854, Asus ROG MAXIMUS V FORMULA, Bundkort, 299.99",
            "N82E16813132247, ASUS MAXIMUS VII FORMULA, Bundkort, 349.99",
            "N82E16813132125, Asus MAXIMUS VII HERO, Bundkort, 214.99",
            "KHX16C9T3K2/8X, Kingston DDR3 HyperX Beast 1600mhz 8GB, Hukommelse (RAM), 112.86",
            "KHX16C9T3K2/16X, Kingston DDR3 HyperX Beast 1600mhz 16GB, Hukommelse (RAM), 199.79",
            "KHX16C9T3K4/32X, Kingston DDR3 HyperX Beast 1600mhz 32GB, Hukommelse (RAM), 346.28",
            "HX324C11T3K2/16, Kingston DDR3 HyperX Beast 2400mhz 16GB, Hukommelse (RAM), 187.62",
            "CMD16GX3M4A1866C9, Corsair Dominator DDR3 1866mhz 16GB, Hukommelse (RAM), 273.84",
            "CMD16GX4M4B3000C14, Corsair Dominator DDR4 3000mhz 16GB, Hukommelse (RAM), 868.90",
            "STRIX-GTX970-DC2OC-4GD5, ASUS GeForce STRIX GTX 970 4GB, Grafikkort, 454.65",
            "04G-P4-2974-KR, EVGA GeForce GTX 970 4GB, Grafikkort, 417.12",
            "GTXTITANX-12GD5, ASUS GeForce GTX TITAN X 12GB, Grafikkort, 1000.00",
            "ZT-90301-10M, ZOTAC GeForce GTX 960 2GB, Grafikkort, 260.63",
            "GV-R928XOC-3GD, Gigabyte Radeon R9 280X 3GB, Grafikkort, 301.20",
            "100361-2SR, SAPPHIRE TRI-X OC 290X 4GB, Grafikkort, 339.99",
            "02G-P4-3753-KR, EVGA GeForce GTX 750Ti 2GB, Grafikkort, 188.25",
            "FD-CA-DEF-R5-BK, Fractal Design Define R5 Black, Kabinetter, 121.59",
            "CC-9011035-WW, Corsair Obsidian 750D Big Tower Black, Kabinetter, 192.54",
            "ROC-11-80, ROCCAT Kone XTD - Max Customization, Mus & Keyboards, 94.49",
            "RZ-01-01040100-R3G1, Razer Naga, Mus & Keyboards, 72.62",
            "90LM00U0-B013L0, Asus 27\" LED G-Sync ROG SWIFT PG278QE, Skærme, 769.81",
            "WD1003FZEX, WD Desktop Black 1TB, Harddiske, 94.44"
        };

        productMap = new CaseInsensitiveKeyMap();

        for (String s : testProducts)
        {
            String[] pInfo = s.split(", ");
            Product p = new Product(pInfo[0], new BigDecimal(pInfo[3]), pInfo[2], pInfo[1]);
            productMap.put(pInfo[0], p);
        }
    }

    public static Map<String, Product> loadProductMap()
    {
        return productMap;
    }

    public static Map<String, Set<Product>> loadTypeMap()
    {
        Map<String, Set<Product>> retVal = new CaseInsensitiveKeyMap();
        for (String s : testProducts)
        {
            String[] pInfo = s.split(", ");
            Set<Product> sp = retVal.get(pInfo[2]);

            if (sp == null)
            {
                sp = new HashSet();
                retVal.put(pInfo[2], sp);
            }

            sp.add(productMap.get(pInfo[0]));
        }

        return retVal;
    }

    public static Map<String, Set<Product>> loadDescriptionMap()
    {
        Map<String, Set<Product>> retVal = new CaseInsensitiveKeyMap();
        for (String s : testProducts)
        {
            String[] pInfo = s.split(", ");
            for (String dTag : (pInfo[1] + " " + pInfo[2]).split(" "))
            {
                Set<Product> sp = retVal.get(dTag);
                if (sp == null)
                {
                    sp = new HashSet();
                    retVal.put(dTag, sp);
                }

                sp.add(productMap.get(pInfo[0]));
            }
        }

        return retVal;
    }

    public static void test()
    {
        Map<String, Set<Product>> typeMap = loadTypeMap();
        Map<String, Set<Product>> descriptionMap = loadDescriptionMap();

        for (String s : typeMap.keySet())
        {
            System.out.println(s + " " + typeMap.get(s));
        }

        for (String s : descriptionMap.keySet())
        {
            System.out.println(s + " " + descriptionMap.get(s));
        }

        for (String s : productMap.keySet())
        {
            System.out.println(s + " " + productMap.get(s));
        }
    }

    public static void main(String[] args)
    {
        test();
    }
}
