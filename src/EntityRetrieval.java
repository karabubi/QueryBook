//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Set;
//import java.util.TreeSet;
//
////import javatools.parsers.PlingStemmer;
//
//import com.hp.hpl.jena.query.Query;
//import com.hp.hpl.jena.query.QueryExecution;
//import com.hp.hpl.jena.query.QueryExecutionFactory;
//import com.hp.hpl.jena.query.QueryFactory;
//import com.hp.hpl.jena.query.QuerySolution;
//import com.hp.hpl.jena.query.ResultSet;
//import com.hp.hpl.jena.query.ResultSetFormatter;
//import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;
//import com.hp.hpl.jena.tdb.index.Index;
//
////import org.apache.jena.larq.LuceneSearch;
//
//import com.hp.hpl.jena.query.*;
//import com.hp.hpl.jena.rdf.model.Model;
//import com.hp.hpl.jena.sparql.util.QueryExecUtils;
//import com.hp.hpl.jena.vocabulary.RDFS;
//
//import org.apache.jena.atlas.lib.StrUtils;
//import org.apache.jena.atlas.logging.LogCtl;
////import org.apache.jena.query.text.EntityDefinition;
////import org.apache.jena.query.text.TextDatasetFactory;
////import org.apache.jena.query.text.TextQuery;
//
//import org.apache.jena.riot.RDFDataMgr;
////import org.apache.lucene.store.Directory;
////import org.apache.lucene.store.RAMDirectory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class EntityRetrieval {
//
// // /################################################
// // ########### retrieval of Entities ##############
// // #################################################
// private double thresholdtheta = 0.4;
//
// public List<ResourceInfo> getIRIsForPatterns(String Pattern,
//   ArrayList Segment) {
//
//  PreProcessing p = PreProcessing.getInstance();
//  System.out.println(" before initialization ");
//  System.out.println(" after initialization ");
//
//  List<ResourceInfo> foundRessources = new ArrayList<ResourceInfo>();
//  String querystring;
//
//  String option = " OPTIONAL {?iri  <http://dbpedia.org/ontology/wikiPageRedirects> ?mainpage . ?mainpage rdfs:label ?mLabel. FILTER( langMatches(lang(?mLabel), \"en\")).} ";
//  querystring = " PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
//    + " SELECT distinct * WHERE { ?iri rdfs:label ?label.   ?label  bif:contains  '"
//    + Pattern + "'  "
//    + " FILTER( langMatches(lang(?label), \"en\")).  " + option
//    + "   }  "; // limit 10 " FILTER regex(str(?iri),
//
//  System.out.println("-------retrieved query is:" + querystring);
//  String urlsever = Constants.urlsever;
//  QueryEngineHTTP qexec = new QueryEngineHTTP(urlsever, querystring);
//  qexec.addDefaultGraph(Constants.DefaultGraphString);
//  Set<String> setiri = new TreeSet<String>();
//
//  try {
//   ResultSet results = qexec.execSelect();
//   while (results.hasNext()) {
//
//    QuerySolution solution = results.nextSolution();
//
//    boolean lanflag = false;
//    ResourceInfo object = new ResourceInfo();
//    String URIName = null, Label = null, mLabel = null;
//    if (solution.get("mainpage") != null) {
//     URIName = solution.get("mainpage").toString();
//     Label = solution.get("mLabel").asLiteral().getLexicalForm();// .toString();
//
//     if (Label.contains("@")) {
//
//      if (Label.endsWith("@en")) {
//
//       lanflag = true;
//       Label = Label.replace("@en", "");
//
//       if (Label.endsWith("\"")) {
//        Label = Label.replace("\"", "");
//       }
//
//       if (Label.startsWith("\"")) {
//        Label = Label.replace("\"", "");
//       }
//
//      }
//
//     }
//
//     else {
//      lanflag = true;
//      if (Label.endsWith("\"")) {
//       Label = Label.replace("\"", "");
//      }
//
//      if (Label.startsWith("\"")) {
//       Label = Label.replace("\"", "");
//      }
//
//     }
//
//    }
//
//    else {
//     URIName = solution.get("iri").toString();
//     Label = solution.get("label").asLiteral().getLexicalForm();
//
//     if (Label.contains("@")) {
//
//      if (Label.endsWith("@en")) {
//
//       lanflag = true;
//       Label = Label.replace("@en", "");
//
//       if (Label.endsWith("\"")) {
//        Label = Label.replace("\"", "");
//       }
//
//       if (Label.startsWith("\"")) {
//        Label = Label.replace("\"", "");
//       }
//
//      }
//
//     }
//
//     else {
//      lanflag = true;
//      if (Label.endsWith("\"")) {
//       Label = Label.replace("\"", "");
//      }
//
//      if (Label.startsWith("\"")) {
//       Label = Label.replace("\"", "");
//      }
//
//     }
//
//    }
//
//    if (URIName.startsWith("http://dbpedia.org/property/")) {
//     object.setType(Constants.TYPE_PROPERTY);
//    } else if (URIName.startsWith("http://dbpedia.org/resource/")) {
//
//     object.setType(Constants.TYPE_INSTANCE);
//    }
//
//    object.setUri(URIName);
//    object.setLabel(Label.trim());
//
//    if (!URIName.startsWith("http://dbpedia.org/ontology/")
//      && !setiri.contains(URIName)) {
//     foundRessources.add(object);
//     setiri.add(URIName);
//
//    }
//
//   }// end of second while
//
//  } catch (Exception e) {
//
//   System.out.println("finish by exeption" + e.getMessage());
//
//  } finally {
//  }
//
//  Functionality f = new Functionality();
//
//  ArrayList LabelList;
//
//  for (ResourceInfo resourceInfo : foundRessources) {
//
//   if (resourceInfo.getUri().startsWith("http://dbpedia.org/resource")) {
//
//    LabelList = p.removeStopWordswithStemming(resourceInfo
//      .getLabel());
//    resourceInfo.setStringSimilarityScore(f
//      .JaccardLevenstienSimilarity(Segment, LabelList,
//        resourceInfo.getLabel()));
//    // System.out.println("##########  list of resources " +
//    // resourceInfo.getUri() + " similarity score " +
//    // resourceInfo.getStringSimilarityScore());
//   }
//
//   else if (resourceInfo.getUri().startsWith(
//     "http://dbpedia.org/property")) {
//    LabelList = p.removeStopWordswithlem(resourceInfo.getLabel());
//    resourceInfo.setStringSimilarityScore(f
//      .JaccardLevenstienSimilarity(Segment, LabelList,
//        resourceInfo.getLabel()));
//
//   }
//  }
//
//  List<ResourceInfo> finalfoundResources = new ArrayList<ResourceInfo>();
//  for (ResourceInfo resourceInfo : foundRessources) {
//   if (resourceInfo.getStringSimilarityScore() >= thresholdtheta) {
//    finalfoundResources.add(resourceInfo);
//   }
//
//  }
//
//  // sort the final list
//  Collections.sort(finalfoundResources, new StringSimilarityComparator());
//  // cut the final list if it is so big
//  if (finalfoundResources.size() > Constants.LimitOfList) {
//   finalfoundResources = finalfoundResources.subList(0,
//     Constants.LimitOfList);
//  }
//
//  // set the associating class of final resources
//
//  /*
//   * for(ResourceInfo resourceInfo: finalfoundResources) {
//   * if(resourceInfo.getType() == Constants.TYPE_INSTANCE)
//   * resourceInfo.setClassSet(getClassofInstance(resourceInfo.getUri()));
//   * }
//   */
//
//  return finalfoundResources;
// }
//
//
//}