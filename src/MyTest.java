import java.util.Scanner;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;


public class MyTest {

	public static void main(String[] args) {
//		System.out.print("enter name: " );
//	Scanner keyboard = new Scanner(System.in);
//		String name = keyboard.nextLine();
//		System.out.println("name: " + name);
//		
      
        
        String s2 = "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> \n" +
        		"PREFIX dbpprop: <http://dbpedia.org/property/> \n" +       		
        		"PREFIX  g:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
                        "PREFIX  rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                        "PREFIX  ontology: <http://dbpedia.org/ontology/>\n" +
                        "\n" +

"SELECT ?Book " +
"WHERE { " +
"  ?Book a dbpedia-owl:Book . " +
"  ?Book dbpprop:author ?author . " +
"  ?author dbpprop:name ?name " +
"  FILTER regex(?name, \"Agatha Christie\", \"i\") " +
"}";
       // System.out.println("debug s2: >>>>>\n" + s2 + "\n<<<<<<");

        Query query = QueryFactory.create(s2); //s2 = the query above
        QueryExecution qExe = QueryExecutionFactory.sparqlService( "http://dbpedia.org/sparql", query );
        ResultSet results = qExe.execSelect();
        ResultSetFormatter.out(System.out, results, query) ;

	}

}

