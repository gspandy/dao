package com.porpoise.api.generator.templates;

import com.porpoise.generator.*;
import java.util.*;
import com.porpoise.api.generator.gen.*;

public class ApiPomTemplate implements IGenerator
{
  protected static String nl;
  public static synchronized ApiPomTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ApiPomTemplate result = new ApiPomTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + NL + "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" + NL + "\txsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">" + NL + "\t<modelVersion>4.0.0</modelVersion>" + NL + "\t<groupId>";
  protected final String TEXT_2 = "</groupId>" + NL + "\t<artifactId>";
  protected final String TEXT_3 = "</artifactId>" + NL + "\t<version>";
  protected final String TEXT_4 = "</version>" + NL + "\t<name>";
  protected final String TEXT_5 = "</name>" + NL + "\t<properties>" + NL + "\t\t<javaDocVersion>2.5</javaDocVersion>" + NL + "\t\t<findbugsVersion>2.0.1</findbugsVersion>" + NL + "\t</properties>" + NL + "" + NL + "\t<dependencies>";
  protected final String TEXT_6 = NL + "\t\t<dependency>" + NL + "\t\t\t<groupId>";
  protected final String TEXT_7 = "</groupId>" + NL + "\t\t\t<artifactId>";
  protected final String TEXT_8 = "</artifactId>" + NL + "\t        <version>";
  protected final String TEXT_9 = "</version>" + NL + "\t\t</dependency>";
  protected final String TEXT_10 = NL + "\t\t<dependency>" + NL + "\t\t\t<groupId>com.porpoise.dao</groupId>" + NL + "\t\t\t<artifactId>dao-common</artifactId>" + NL + "\t        <version>0.0.1-SNAPSHOT</version>" + NL + "\t\t</dependency>" + NL + "\t\t<dependency>" + NL + "\t\t\t<groupId>junit</groupId>" + NL + "\t\t\t<artifactId>junit</artifactId>" + NL + "\t\t\t<version>4.4</version>" + NL + "\t\t\t<scope>test</scope>" + NL + "\t\t</dependency>" + NL + "\t\t<dependency>" + NL + "\t\t\t<groupId>log4j</groupId>" + NL + "\t\t\t<artifactId>log4j</artifactId>" + NL + "\t\t\t<version>1.2.14</version>" + NL + "\t\t</dependency>" + NL + "\t\t<dependency>" + NL + "\t\t\t<groupId>com.google.guava</groupId>" + NL + "\t\t\t<artifactId>guava</artifactId>" + NL + "\t\t\t<version>r06</version>" + NL + "\t\t</dependency>" + NL + "" + NL + "\t\t<dependency>" + NL + "\t\t\t<groupId>org.apache.derby</groupId>" + NL + "\t\t\t<artifactId>derby</artifactId>" + NL + "\t\t\t<version>10.5.3.0_1</version>" + NL + "\t\t\t<scope>test</scope>" + NL + "\t\t</dependency>" + NL + "\t\t" + NL + "\t\t<dependency>" + NL + "\t\t\t<groupId>mysql</groupId>" + NL + "\t\t\t<artifactId>mysql-connector-java</artifactId>" + NL + "\t\t\t<version>5.1.9</version>" + NL + "\t\t\t<scope>test</scope>" + NL + "\t\t</dependency>" + NL + "\t\t" + NL + "<!-- " + NL + "\t\t<dependency>" + NL + "\t\t\t<groupId>com.oracle</groupId>" + NL + "" + NL + "\t\t\t<artifactId>classes12</artifactId>" + NL + "\t\t\t<version>10.2.0.2.0</version>" + NL + "" + NL + "or" + NL + "\t\t\t<artifactId>ojdbc14</artifactId>" + NL + "\t\t\t<version>10.2.0.3.0</version>" + NL + "" + NL + "\t\t\t<scope>test</scope>" + NL + "\t\t</dependency>" + NL + " -->" + NL + "" + NL + "\t</dependencies>" + NL + "" + NL + "\t<build>" + NL + "\t\t<plugins>" + NL + "\t\t\t<plugin>" + NL + "\t\t\t\t<groupId>org.apache.maven.plugins</groupId>" + NL + "\t\t\t\t<artifactId>maven-compiler-plugin</artifactId>" + NL + "\t\t\t\t<configuration>" + NL + "\t\t\t\t\t<source>1.6</source>" + NL + "\t\t\t\t\t<target>1.6</target>" + NL + "\t\t\t\t\t<inherit>true</inherit>" + NL + "\t\t\t\t</configuration>" + NL + "\t\t\t</plugin>" + NL + "" + NL + "\t\t\t<plugin>" + NL + "\t\t\t\t<groupId>org.apache.maven.plugins</groupId>" + NL + "\t\t\t\t<artifactId>maven-surefire-plugin</artifactId>" + NL + "\t\t\t\t<configuration>" + NL + "\t\t\t\t\t<testFailureIgnore>false</testFailureIgnore>" + NL + "\t\t\t\t</configuration>" + NL + "\t\t\t</plugin>" + NL + "\t\t</plugins>" + NL + "" + NL + "\t\t<pluginManagement>" + NL + "\t\t\t<plugins>" + NL + "\t\t\t\t<plugin>" + NL + "\t\t\t\t\t<groupId>org.apache.maven.plugins</groupId>" + NL + "\t\t\t\t\t<artifactId>maven-site-plugin</artifactId>" + NL + "\t\t\t\t\t<version>2.0</version>" + NL + "\t\t\t\t</plugin>" + NL + "" + NL + "\t\t\t\t<plugin>" + NL + "\t\t\t\t\t<groupId>org.apache.maven.plugins</groupId>" + NL + "\t\t\t\t\t<artifactId>maven-site-plugin</artifactId>" + NL + "\t\t\t\t\t<version>2.0</version>" + NL + "\t\t\t\t</plugin>" + NL + "" + NL + "\t\t\t\t<plugin>" + NL + "\t\t\t\t\t<groupId>org.codehaus.mojo</groupId>" + NL + "\t\t\t\t\t<artifactId>findbugs-maven-plugin</artifactId>" + NL + "\t\t\t\t\t<version>${findbugsVersion}</version>" + NL + "\t\t\t\t\t<configuration>" + NL + "\t\t\t\t\t\t<findbugsXmlOutput>true</findbugsXmlOutput>" + NL + "\t\t\t\t\t\t<findbugsXmlWithMessages>true</findbugsXmlWithMessages>" + NL + "\t\t\t\t\t\t<xmlOutput>true</xmlOutput>" + NL + "\t\t\t\t\t</configuration>" + NL + "\t\t\t\t</plugin>" + NL + "" + NL + "\t\t\t\t<plugin>" + NL + "\t\t\t\t\t<artifactId>maven-javadoc-plugin</artifactId>" + NL + "\t\t\t\t\t<version>${javaDocVersion}</version>" + NL + "\t\t\t\t</plugin>" + NL + "" + NL + "\t\t\t\t<plugin>" + NL + "\t\t\t\t\t<groupId>com.xebia.mojo</groupId>" + NL + "\t\t\t\t\t<artifactId>maven-dashboard-plugin</artifactId>" + NL + "\t\t\t\t\t<configuration>" + NL + "\t\t\t\t\t\t<destinationFile>${project.reporting.outputDirectory}/maven-dashboard-report.html</destinationFile>" + NL + "\t\t\t\t\t</configuration>" + NL + "\t\t\t\t</plugin>" + NL + "" + NL + "\t\t\t\t<plugin>" + NL + "\t\t\t\t\t<groupId>org.apache.maven.plugins</groupId>" + NL + "\t\t\t\t\t<artifactId>maven-jar-plugin</artifactId>" + NL + "\t\t\t\t\t<executions>" + NL + "\t\t\t\t\t\t<execution>" + NL + "\t\t\t\t\t\t\t<goals>" + NL + "\t\t\t\t\t\t\t\t<goal>test-jar</goal>" + NL + "\t\t\t\t\t\t\t</goals>" + NL + "\t\t\t\t\t\t</execution>" + NL + "\t\t\t\t\t</executions>" + NL + "\t\t\t\t</plugin>" + NL + "\t\t\t\t<plugin>" + NL + "\t\t\t\t\t<groupId>org.apache.maven.plugins</groupId>" + NL + "\t\t\t\t\t<artifactId>maven-surefire-plugin</artifactId>" + NL + "\t\t\t\t\t<configuration>" + NL + "\t\t\t\t\t\t<includes>" + NL + "\t\t\t\t\t\t\t<include>**/*Test.java</include>" + NL + "\t\t\t\t\t\t</includes>" + NL + "\t\t\t\t\t</configuration>" + NL + "\t\t\t\t</plugin>" + NL + "" + NL + "\t\t\t</plugins>" + NL + "\t\t</pluginManagement>" + NL + "\t</build>" + NL + "" + NL + "" + NL + "\t<reporting>" + NL + "\t\t<plugins>" + NL + "" + NL + "\t\t\t<plugin>" + NL + "\t\t\t\t<groupId>com.xebia.mojo</groupId>" + NL + "\t\t\t\t<artifactId>maven-dashboard-plugin</artifactId>" + NL + "\t\t\t</plugin>" + NL + "" + NL + "\t\t\t<plugin>" + NL + "\t\t\t\t<artifactId>maven-javadoc-plugin</artifactId>" + NL + "\t\t\t\t<version>${javaDocVersion}</version>" + NL + "\t\t\t\t<configuration>" + NL + "\t\t\t\t\t<source>1.5</source>" + NL + "\t\t\t\t\t<doclet>gr.spinellis.umlgraph.doclet.UmlGraphDoc</doclet>" + NL + "\t\t\t\t\t<docletArtifact>" + NL + "\t\t\t\t\t\t<groupId>gr.spinellis</groupId>" + NL + "\t\t\t\t\t\t<artifactId>UmlGraph</artifactId>" + NL + "\t\t\t\t\t\t<version>4.6</version>" + NL + "\t\t\t\t\t</docletArtifact>" + NL + "\t\t\t\t\t<additionalparam>" + NL + "\t\t\t\t\t\t-inferrel -inferdep -quiet -hide java.*" + NL + "\t\t\t\t\t\t-collpackages java.util.* -qualify" + NL + "\t\t\t\t\t\t-postfixpackage -nodefontsize 9" + NL + "\t\t\t\t\t\t-nodefontpackagesize 7" + NL + "                    </additionalparam>" + NL + "\t\t\t\t</configuration>" + NL + "\t\t\t</plugin>" + NL + "" + NL + "\t\t\t<plugin>" + NL + "\t\t\t\t<groupId>org.apache.maven.plugins</groupId>" + NL + "\t\t\t\t<artifactId>maven-project-info-reports-plugin</artifactId>" + NL + "\t\t\t\t<version>2.1</version>" + NL + "\t\t\t\t<configuration>" + NL + "\t\t\t\t\t<dependencyLocationsEnabled>false</dependencyLocationsEnabled>" + NL + "\t\t\t\t\t<dependencyDetailsEnabled>false</dependencyDetailsEnabled>" + NL + "\t\t\t\t</configuration>" + NL + "\t\t\t</plugin>" + NL + "\t\t\t<plugin>" + NL + "\t\t\t\t<groupId>org.codehaus.mojo</groupId>" + NL + "\t\t\t\t<artifactId>taglist-maven-plugin</artifactId>" + NL + "\t\t\t</plugin>" + NL + "\t\t\t<plugin>" + NL + "\t\t\t\t<groupId>org.codehaus.mojo</groupId>" + NL + "\t\t\t\t<artifactId>jxr-maven-plugin</artifactId>" + NL + "\t\t\t</plugin>" + NL + "" + NL + "\t\t\t<plugin>" + NL + "\t\t\t\t<groupId>org.codehaus.mojo</groupId>" + NL + "\t\t\t\t<artifactId>findbugs-maven-plugin</artifactId>" + NL + "\t\t\t\t<version>${findbugsVersion}</version>" + NL + "\t\t\t\t<configuration>" + NL + "\t\t\t\t\t<effort>Max</effort>" + NL + "\t\t\t\t\t<findbugsXmlOutput>true</findbugsXmlOutput>" + NL + "\t\t\t\t\t<findbugsXmlWithMessages>true</findbugsXmlWithMessages>" + NL + "\t\t\t\t\t<xmlOutput>true</xmlOutput>" + NL + "\t\t\t\t</configuration>" + NL + "\t\t\t</plugin>" + NL + "" + NL + "\t\t\t<plugin>" + NL + "\t\t\t\t<groupId>org.apache.maven.plugins</groupId>" + NL + "\t\t\t\t<artifactId>maven-surefire-report-plugin</artifactId>" + NL + "\t\t\t</plugin>" + NL + "" + NL + "\t\t\t<plugin>" + NL + "\t\t\t\t<groupId>org.apache.maven.plugins</groupId>" + NL + "\t\t\t\t<artifactId>maven-checkstyle-plugin</artifactId>" + NL + "\t\t\t</plugin>" + NL + "" + NL + "\t\t\t<plugin>" + NL + "\t\t\t\t<groupId>org.apache.maven.plugins</groupId>" + NL + "\t\t\t\t<artifactId>maven-pmd-plugin</artifactId>" + NL + "\t\t\t\t<configuration>" + NL + "\t\t\t\t\t<rulesets>" + NL + "\t\t\t\t\t\t<ruleset>/rulesets/basic.xml</ruleset>" + NL + "\t\t\t\t\t\t<ruleset>/rulesets/controversial.xml</ruleset>" + NL + "\t\t\t\t\t</rulesets>" + NL + "\t\t\t\t\t<sourceEncoding>utf-8</sourceEncoding>" + NL + "\t\t\t\t\t<minimumTokens>100</minimumTokens>" + NL + "\t\t\t\t\t<targetJdk>1.6</targetJdk>" + NL + "\t\t\t\t</configuration>" + NL + "\t\t\t</plugin>" + NL + "\t\t</plugins>" + NL + "\t</reporting>" + NL + "" + NL + "</project>";

   /* (non-javadoc)
    * @see IGenerator#generate(Object)
    */
   public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     final PomContext ctxt = (PomContext) argument; 

    stringBuffer.append(TEXT_1);
    stringBuffer.append( ctxt.getGroupId() );
    stringBuffer.append(TEXT_2);
    stringBuffer.append( ctxt.getArtifactId() );
    stringBuffer.append(TEXT_3);
    stringBuffer.append( ctxt.getVersion() );
    stringBuffer.append(TEXT_4);
    stringBuffer.append( ctxt.getName() );
    stringBuffer.append(TEXT_5);
     for(final PomContext d : ctxt.getDependencies()) { 
    stringBuffer.append(TEXT_6);
    stringBuffer.append( d.getGroupId() );
    stringBuffer.append(TEXT_7);
    stringBuffer.append( d.getArtifactId() );
    stringBuffer.append(TEXT_8);
    stringBuffer.append( d.getVersion() );
    stringBuffer.append(TEXT_9);
     } 
    stringBuffer.append(TEXT_10);
    return stringBuffer.toString();
  }
}