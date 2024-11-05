package lp1ass2;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

class Tuple {
    String mnemonic, m_class, opcode;
    int length;
   
    Tuple() {}
   
    Tuple(String s1, String s2, String s3, String s4) {
        mnemonic = s1;
        m_class = s2; 
        opcode = s3;
        length = Integer.parseInt(s4);
    }
}

class SymTuple {
    String symbol, address, length;
  
    SymTuple(String s1, String s2, String i1) {
        symbol = s1;
        address = s2;
        length = i1;
    }
}

class LitTuple {
    String literal, address, length;
  
    LitTuple() {}
  
    LitTuple(String s1, String s2, String i1) {
        literal = s1;
        address = s2;
        length = i1;  
    }
}

public class Assembler_PassTwo {
  
    static int lc, iSymTabPtr = 0, iLitTabPtr = 0, iPoolTabPtr = 0;
    static int poolTable[] = new int[10];
    static ArrayList<SymTuple> symtable;
    static ArrayList<LitTuple> littable;
    static Map<String, String> regAddressTable;
    static PrintWriter out_pass2;

    static void initiallizeTables() throws Exception {
        symtable = new ArrayList<>();
        littable = new ArrayList<>();
        regAddressTable = new HashMap<>();
        String s;
        BufferedReader br;

        // Read the symbol table
        br = new BufferedReader(new InputStreamReader(new FileInputStream("src/lp1/symtable.txt")));
        while((s = br.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(s, "\t", false);
            symtable.add(new SymTuple(st.nextToken(), st.nextToken(), ""));
        }
        br.close();

        // Read the literal table
        br = new BufferedReader(new InputStreamReader(new FileInputStream("src/lp1/littable.txt")));
        while((s = br.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(s, "\t", false);
            littable.add(new LitTuple(st.nextToken(), st.nextToken(), ""));
        }
        br.close();

        // Initialize register address table
        regAddressTable.put("AREG", "1");
        regAddressTable.put("BREG", "2");
        regAddressTable.put("CREG", "3");
        regAddressTable.put("DREG", "4");
    }
 
    static void pass2() throws Exception {
        BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream("src/lp1/output_pass1.txt")));
        out_pass2 = new PrintWriter(new FileWriter("src/lp1/output_pass2.txt"), true);
//        out_pass2.println("Swarup Kharat Roll No: 5");
        String s;
  
        // Read from intermediate file one line at a time
        while ((s = input.readLine()) != null) {
            // Replace all ( and ) characters by a blank string
            s = s.replaceAll("(\\()", " ");
            s = s.replaceAll("(\\))", " ");

            // For each line, separate out the tokens
            String ic_tokens[] = tokenizeString(s, " ");
            if (ic_tokens == null || ic_tokens.length == 0) {
                continue;
            }

            String output_str = "";
            String mnemonic_class = ic_tokens[1];
            String m_tokens[] = tokenizeString(mnemonic_class, ",");

            // Process IS (Imperative Statement)
            if (m_tokens[0].equalsIgnoreCase("IS")) {
                output_str += ic_tokens[0] + " "; // location counter
                output_str += m_tokens[1] + " ";  // opcode

                for (int i = 2; i < ic_tokens.length; i++) {
                    String opr_tokens[] = tokenizeString(ic_tokens[i], ",");
                    if (opr_tokens[0].equalsIgnoreCase("RG")) {
                        output_str += opr_tokens[1] + " ";  // register code
                    } else if (opr_tokens[0].equalsIgnoreCase("S")) {
                        int index = Integer.parseInt(opr_tokens[1]);
                        output_str += symtable.get(index).address + " ";  // symbol address
                    } else if (opr_tokens[0].equalsIgnoreCase("L")) {
                        int index = Integer.parseInt(opr_tokens[1]);
                        output_str += littable.get(index).address + " ";  // literal address
                    }
                }
            }
            // Process DL (Declarative Statement)
            else if (m_tokens[0].equalsIgnoreCase("DL")) {
                output_str += ic_tokens[0] + " "; // location counter
                if (m_tokens[1].equalsIgnoreCase("02")) {
                    String opr_tokens[] = tokenizeString(ic_tokens[2], ",");
                    output_str += "00 00 " + opr_tokens[1] + " "; // constant value
                }
            }

            System.out.println(output_str);
            out_pass2.println(output_str);
        }
        input.close();
        out_pass2.close();
    }

    static String[] tokenizeString(String str, String separator) {
        StringTokenizer st = new StringTokenizer(str, separator, false);
        String s_arr[] = new String[st.countTokens()];
        for (int i = 0; i < s_arr.length; i++) {
            s_arr[i] = st.nextToken();
        }
        return s_arr;
    }

    public static void main(String[] args) throws Exception {
//        System.out.println("Swarup Kharat");
//        System.out.println("Roll No: 22105");
        initiallizeTables();
        System.out.println("============PASS TWO OUTPUT===========");
        pass2();
    }
}
