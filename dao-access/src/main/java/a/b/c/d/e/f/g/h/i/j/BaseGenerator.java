package a.b.c.d.e.f.g.h.i.j;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.porpoise.api.generator.gen.ApiGenerator;
import com.porpoise.api.generator.gen.ApiProjectDefinition;
import com.porpoise.api.generator.model.DomainObject;
import com.porpoise.dao.generator.gen.DaoGenerator;
import com.porpoise.dao.generator.gen.ProjectDefinition;
import com.porpoise.dao.generator.gen.access.AccessTable;
import com.porpoise.dao.generator.model.Column;
import com.porpoise.dao.generator.model.Table;
import com.porpoise.generator.model.FieldType;

/**
 * Generator class used to create source code/resources from an in-memory schema
 */
public class BaseGenerator {
	private final ImmutableList<Table> tables;
	// =======================================================================================
	// ALERT_EXAMPLES
	// =======================================================================================
	public final Table ALERT_EXAMPLES;
	public final Column ALERT_EXAMPLES__ALERT_ID;
	public final Column ALERT_EXAMPLES__EXAMPLE_ID;
	public final Column ALERT_EXAMPLES__CREATION_DATE;
	// =======================================================================================
	// ALERT_ORGAN
	// =======================================================================================
	public final Table ALERT_ORGAN;
	public final Column ALERT_ORGAN__ALERT_ID;
	public final Column ALERT_ORGAN__ORGAN_ID;
	public final Column ALERT_ORGAN__CREATION_DATE;
	// =======================================================================================
	// ALERT_REFS
	// =======================================================================================
	public final Table ALERT_REFS;
	public final Column ALERT_REFS__ALERT_ID;
	public final Column ALERT_REFS__REF_ID;
	public final Column ALERT_REFS__CREATION_DATE;
	// =======================================================================================
	// ALERT_SPECIES
	// =======================================================================================
	public final Table ALERT_SPECIES;
	public final Column ALERT_SPECIES__ALERT_ID;
	public final Column ALERT_SPECIES__SPECIES_ID;
	public final Column ALERT_SPECIES__CREATION_DATE;
	// =======================================================================================
	// ALERTS
	// =======================================================================================
	public final Table ALERTS;
	public final Column ALERTS__ALERT_ID;
	public final Column ALERTS__ALERT_NAME;
	public final Column ALERTS__ALERT_DESCRIPTION;
	public final Column ALERTS__COMMENTS;
	public final Column ALERTS__VIEWED_NUMBER;
	public final Column ALERTS__ACTIVE;
	public final Column ALERTS__RULEWRITER_COMMENTS;
	public final Column ALERTS__RULEWRITER_ID;
	public final Column ALERTS__CREATION_DATE;
	public final Column ALERTS__LAST_MODIFIED_DATE;
	public final Column ALERTS__EXPOSURE_ROUTE_ID;
	public final Column ALERTS__DISTRIBUTION_ID;
	public final Column ALERTS__PHASE_ID;
	public final Column ALERTS__CONFIDENCE_ID;
	// =======================================================================================
	// ASSAY
	// =======================================================================================
	public final Table ASSAY;
	public final Column ASSAY__ASSAY_ID;
	public final Column ASSAY__ASSAY;
	public final Column ASSAY__CREATION_DATE;
	// =======================================================================================
	// ASSAY_DATA
	// =======================================================================================
	public final Table ASSAY_DATA;
	public final Column ASSAY_DATA__ASSAY_DATA_ID;
	public final Column ASSAY_DATA__BIO_ID;
	public final Column ASSAY_DATA__ASSAY_SOURCE;
	public final Column ASSAY_DATA__ASSAY_TIME;
	public final Column ASSAY_DATA__QUANTIFICATION_OF_RESULTS;
	public final Column ASSAY_DATA__CHEM_CHARAC_PARAMS;
	public final Column ASSAY_DATA__CREATION_DATE;
	// =======================================================================================
	// BIODATA
	// =======================================================================================
	public final Table BIODATA;
	public final Column BIODATA__BIO_ID;
	public final Column BIODATA__EXAMPLE_ID;
	public final Column BIODATA__SPECIES_ID;
	public final Column BIODATA__ASSAY_ID;
	public final Column BIODATA__DATA;
	public final Column BIODATA__IN_VITRO;
	public final Column BIODATA__ROUTE_OF_ADMIN;
	public final Column BIODATA__DOSE_LEVELS;
	public final Column BIODATA__SINGLE_DOSE;
	public final Column BIODATA__MULTI_DOSE;
	public final Column BIODATA__DOSE_FREQUENCY;
	public final Column BIODATA__TEST_SYSTEM;
	public final Column BIODATA__CREATION_DATE;
	// =======================================================================================
	// BIODATA_REFS
	// =======================================================================================
	public final Table BIODATA_REFS;
	public final Column BIODATA_REFS__REF_ID;
	public final Column BIODATA_REFS__BIO_ID;
	public final Column BIODATA_REFS__CREATION_DATE;
	// =======================================================================================
	// COMPONENT
	// =======================================================================================
	public final Table COMPONENT;
	public final Column COMPONENT__Component_ID;
	public final Column COMPONENT__Pattern_ID;
	public final Column COMPONENT__Stage_Number;
	public final Column COMPONENT__Sequence;
	public final Column COMPONENT__Creation_Date;
	// =======================================================================================
	// COMPONENT_INTERMEDIATE
	// =======================================================================================
	public final Table COMPONENT_INTERMEDIATE;
	public final Column COMPONENT_INTERMEDIATE__Component_ID;
	public final Column COMPONENT_INTERMEDIATE__Intermediate_ID;
	public final Column COMPONENT_INTERMEDIATE__Creation_Date;
	// =======================================================================================
	// DATABASE_DATES
	// =======================================================================================
	public final Table DATABASE_DATES;
	public final Column DATABASE_DATES__CREATION_DATE;
	// =======================================================================================
	// ENTITIES
	// =======================================================================================
	public final Table ENTITIES;
	public final Column ENTITIES__ENTITY_ID;
	public final Column ENTITIES__ENTITY_DESCRIPTION;
	public final Column ENTITIES__CREATION_DATE;
	// =======================================================================================
	// ENZYME
	// =======================================================================================
	public final Table ENZYME;
	public final Column ENZYME__ENZYME_ID;
	public final Column ENZYME__ENZYME;
	public final Column ENZYME__CREATION_DATE;
	// =======================================================================================
	// EXAMPLE_PATTERNS
	// =======================================================================================
	public final Table EXAMPLE_PATTERNS;
	public final Column EXAMPLE_PATTERNS__EXAMPLE_ID;
	public final Column EXAMPLE_PATTERNS__PATTERN_ID;
	public final Column EXAMPLE_PATTERNS__REACTION_STAGE;
	public final Column EXAMPLE_PATTERNS__CREATION_DATE;
	// =======================================================================================
	// EXAMPLES
	// =======================================================================================
	public final Table EXAMPLES;
	public final Column EXAMPLES__EXAMPLE_ID;
	public final Column EXAMPLES__EXAMPLE_NAME;
	public final Column EXAMPLES__CAS_NO;
	public final Column EXAMPLES__EXAMPLE_PIC;
	public final Column EXAMPLES__COMMENTS;
	public final Column EXAMPLES__ACTIVE;
	public final Column EXAMPLES__PATTERN_ID;
	public final Column EXAMPLES__RULEWRITER_COMMENTS;
	public final Column EXAMPLES__CREATION_DATE;
	// =======================================================================================
	// EXPOSURE_ROUTES
	// =======================================================================================
	public final Table EXPOSURE_ROUTES;
	public final Column EXPOSURE_ROUTES__EXPOSURE_ROUTE_ID;
	public final Column EXPOSURE_ROUTES__ROUTE_OF_EXPOSURE;
	public final Column EXPOSURE_ROUTES__CREATION_DATE;
	// =======================================================================================
	// EXTERNAL_EXAMPLES
	// =======================================================================================
	public final Table EXTERNAL_EXAMPLES;
	public final Column EXTERNAL_EXAMPLES__EXTERNAL_EXAMPLE_ID;
	public final Column EXTERNAL_EXAMPLES__ALERT_ID;
	public final Column EXTERNAL_EXAMPLES__EXTERNAL_PRODUCT_NAME;
	public final Column EXTERNAL_EXAMPLES__CREATION_DATE;
	// =======================================================================================
	// FORCE
	// =======================================================================================
	public final Table FORCE;
	public final Column FORCE__Force_ID;
	public final Column FORCE__Force;
	public final Column FORCE__CREATION_DATE;
	// =======================================================================================
	// GROUNDS_THRESHOLD
	// =======================================================================================
	public final Table GROUNDS_THRESHOLD;
	public final Column GROUNDS_THRESHOLD__RULE_ID;
	public final Column GROUNDS_THRESHOLD__COUNTER;
	public final Column GROUNDS_THRESHOLD__GROUNDS_ENTITY_ID;
	public final Column GROUNDS_THRESHOLD__THRESHOLD_ENTITY_ID;
	public final Column GROUNDS_THRESHOLD__CREATION_DATE;
	// =======================================================================================
	// INTERMEDIATES
	// =======================================================================================
	public final Table INTERMEDIATES;
	public final Column INTERMEDIATES__ID;
	public final Column INTERMEDIATES__Title;
	public final Column INTERMEDIATES__Prefix;
	public final Column INTERMEDIATES__Creation_Date;
	// =======================================================================================
	// LEVELS_OF_CONFIDENCE
	// =======================================================================================
	public final Table LEVELS_OF_CONFIDENCE;
	public final Column LEVELS_OF_CONFIDENCE__CONFIDENCE_ID;
	public final Column LEVELS_OF_CONFIDENCE__CONFIDENCE_LEVEL;
	public final Column LEVELS_OF_CONFIDENCE__CREATION_DATE;
	// =======================================================================================
	// ORGAN_ENZYME
	// =======================================================================================
	public final Table ORGAN_ENZYME;
	public final Column ORGAN_ENZYME__ORGAN_ID;
	public final Column ORGAN_ENZYME__ENZYME_ID;
	public final Column ORGAN_ENZYME__CREATION_DATE;
	// =======================================================================================
	// ORGAN_EXPOSURE
	// =======================================================================================
	public final Table ORGAN_EXPOSURE;
	public final Column ORGAN_EXPOSURE__ORGAN_ID;
	public final Column ORGAN_EXPOSURE__EXPOSURE_ROUTE_ID;
	public final Column ORGAN_EXPOSURE__CREATION_DATE;
	// =======================================================================================
	// ORGANS
	// =======================================================================================
	public final Table ORGANS;
	public final Column ORGANS__ORGAN_ID;
	public final Column ORGANS__TARGET_ORGAN;
	public final Column ORGANS__CREATION_DATE;
	// =======================================================================================
	// PATTERNS
	// =======================================================================================
	public final Table PATTERNS;
	public final Column PATTERNS__PATTERN_ID;
	public final Column PATTERNS__ALERT_ID;
	public final Column PATTERNS__PATTERN;
	public final Column PATTERNS__INCLUSION_PATTERN;
	public final Column PATTERNS__PATTERN_KEY;
	public final Column PATTERNS__PATTERN_KEY2;
	public final Column PATTERNS__PATTERN_KEY3;
	public final Column PATTERNS__CREATION_DATE;
	// =======================================================================================
	// PHASE
	// =======================================================================================
	public final Table PHASE;
	public final Column PHASE__PHASE_ID;
	public final Column PHASE__PHASE_EXPRESSION;
	public final Column PHASE__CREATION_DATE;
	// =======================================================================================
	// PHYSCHEM
	// =======================================================================================
	public final Table PHYSCHEM;
	public final Column PHYSCHEM__PhysChem_ID;
	public final Column PHYSCHEM__Name;
	public final Column PHYSCHEM__Comment;
	public final Column PHYSCHEM__ReturnType;
	public final Column PHYSCHEM__CREATION_DATE;
	// =======================================================================================
	// PRODUCT_DISTRIBUTION
	// =======================================================================================
	public final Table PRODUCT_DISTRIBUTION;
	public final Column PRODUCT_DISTRIBUTION__DISTRIBUTION_ID;
	public final Column PRODUCT_DISTRIBUTION__DISTRIBUTION_EXPRESSION;
	public final Column PRODUCT_DISTRIBUTION__CREATION_DATE;
	// =======================================================================================
	// REFERENCES
	// =======================================================================================
	public final Table REFERENCES;
	public final Column REFERENCES__REF_ID;
	public final Column REFERENCES__TITLE;
	public final Column REFERENCES__AUTHORS;
	public final Column REFERENCES__VOLUME;
	public final Column REFERENCES__PAGES;
	public final Column REFERENCES__YEAR;
	public final Column REFERENCES__SOURCE_ID;
	public final Column REFERENCES__CREATION_DATE;
	// =======================================================================================
	// RELATIVE
	// =======================================================================================
	public final Table RELATIVE;
	public final Column RELATIVE__RELATIVE_ID;
	public final Column RELATIVE__VALUE1;
	public final Column RELATIVE__OPERATOR;
	public final Column RELATIVE__VALUE2;
	public final Column RELATIVE__ACTIVE;
	public final Column RELATIVE__NAME;
	public final Column RELATIVE__COMMENT;
	public final Column RELATIVE__RULEWRITER_COMMENT;
	public final Column RELATIVE__CREATION_DATE;
	// =======================================================================================
	// RELATIVE_REF
	// =======================================================================================
	public final Table RELATIVE_REF;
	public final Column RELATIVE_REF__RELATIVE_ID;
	public final Column RELATIVE_REF__REF_ID;
	public final Column RELATIVE_REF__CREATION_DATE;
	// =======================================================================================
	// RULE_REF
	// =======================================================================================
	public final Table RULE_REF;
	public final Column RULE_REF__RULE_ID;
	public final Column RULE_REF__REF_ID;
	public final Column RULE_REF__CREATION_DATE;
	// =======================================================================================
	// RULES
	// =======================================================================================
	public final Table RULES;
	public final Column RULES__RULE_ID;
	public final Column RULES__RULE_NAME;
	public final Column RULES__PROPOSITION_ENTITY_ID;
	public final Column RULES__FORCE_ENTITY_ID;
	public final Column RULES__RULE_ACTIVE;
	public final Column RULES__RULE_FAMILY;
	public final Column RULES__COMMENTS;
	public final Column RULES__RULEWRITERS_COMMENTS;
	public final Column RULES__CREATION_DATE;
	// =======================================================================================
	// SOURCE_REF
	// =======================================================================================
	public final Table SOURCE_REF;
	public final Column SOURCE_REF__SOURCE_ID;
	public final Column SOURCE_REF__SOURCE_TITLE;
	public final Column SOURCE_REF__SOURCE_TYPE;
	public final Column SOURCE_REF__CREATION_DATE;
	// =======================================================================================
	// SPECIES
	// =======================================================================================
	public final Table SPECIES;
	public final Column SPECIES__SPECIES_ID;
	public final Column SPECIES__SPECIES;
	public final Column SPECIES__CREATION_DATE;
	// =======================================================================================
	// SPECIES_ENZYME
	// =======================================================================================
	public final Table SPECIES_ENZYME;
	public final Column SPECIES_ENZYME__SPECIES_ID;
	public final Column SPECIES_ENZYME__ENZYME_ID;
	public final Column SPECIES_ENZYME__CREATION_DATE;
	// =======================================================================================
	// SPECIES_ORGAN
	// =======================================================================================
	public final Table SPECIES_ORGAN;
	public final Column SPECIES_ORGAN__SPECIES_ID;
	public final Column SPECIES_ORGAN__ORGAN_ID;
	public final Column SPECIES_ORGAN__CREATION_DATE;
	// =======================================================================================
	// SUPPLEMENTAL
	// =======================================================================================
	public final Table SUPPLEMENTAL;
	public final Column SUPPLEMENTAL__REF_ID;
	public final Column SUPPLEMENTAL__SUPP_DATA;
	public final Column SUPPLEMENTAL__CREATION_DATE;
	// =======================================================================================
	// TAXONS
	// =======================================================================================
	public final Table TAXONS;
	public final Column TAXONS__SPECIES_ID;
	public final Column TAXONS__HIGHER_TAXON_ID;
	public final Column TAXONS__CREATION_DATE;
	// =======================================================================================
	// TEMPLATES
	// =======================================================================================
	public final Table TEMPLATES;
	public final Column TEMPLATES__template_id;
	public final Column TEMPLATES__template_name;
	public final Column TEMPLATES__template_string;
	public final Column TEMPLATES__CREATION_DATE;

	public static void main(final String[] args) throws IOException {
		final Collection<Table> tables = new BaseGenerator().getTables();

		final File dest = new File(System.getProperty("user.dir"),
				"generator-main");

		final File dao = new File(dest, "dao");
		final File api = new File(dest, "api");

		final String packageName = args.length == 1 ? args[0] : "com.example";
		final String group = packageName;

		new DaoGenerator().generateProject(new ProjectDefinition(tables, dao,
				group, "main-test-dao", "1.0.0", packageName));

		final Collection<DomainObject> objects = null;
		new ApiGenerator().generateProject(new ApiProjectDefinition(objects,
				api, group, "api", "1.0.0", packageName));
	}

	/**
	 * @return the tables;
	 */
	public Collection<Table> getTables() {
		return this.tables;
	}

	private static Table newTable(final String name) {
		return new AccessTable(name);
	}

	/**
	 * default constructor
	 */
	public BaseGenerator() throws IOException {
		final List<Table> allTables = Lists.newArrayList();

		// =======================================================================================
		// ALERT_EXAMPLES
		// =======================================================================================
		ALERT_EXAMPLES = newTable("ALERT_EXAMPLES");
		ALERT_EXAMPLES__ALERT_ID = ALERT_EXAMPLES.addColumn("ALERT_ID", false,
				FieldType.Integer);
		ALERT_EXAMPLES__EXAMPLE_ID = ALERT_EXAMPLES.addColumn("EXAMPLE_ID",
				false, FieldType.Integer);
		ALERT_EXAMPLES__CREATION_DATE = ALERT_EXAMPLES.addColumn(
				"CREATION_DATE", false, FieldType.Timestamp);
		allTables.add(ALERT_EXAMPLES);
		// =======================================================================================
		// ALERT_ORGAN
		// =======================================================================================
		ALERT_ORGAN = newTable("ALERT_ORGAN");
		ALERT_ORGAN__ALERT_ID = ALERT_ORGAN.addColumn("ALERT_ID", false,
				FieldType.Integer);
		ALERT_ORGAN__ORGAN_ID = ALERT_ORGAN.addColumn("ORGAN_ID", false,
				FieldType.Integer);
		ALERT_ORGAN__CREATION_DATE = ALERT_ORGAN.addColumn("CREATION_DATE",
				false, FieldType.Timestamp);
		allTables.add(ALERT_ORGAN);
		// =======================================================================================
		// ALERT_REFS
		// =======================================================================================
		ALERT_REFS = newTable("ALERT_REFS");
		ALERT_REFS__ALERT_ID = ALERT_REFS.addColumn("ALERT_ID", false,
				FieldType.Integer);
		ALERT_REFS__REF_ID = ALERT_REFS.addColumn("REF_ID", false,
				FieldType.Integer);
		ALERT_REFS__CREATION_DATE = ALERT_REFS.addColumn("CREATION_DATE",
				false, FieldType.Timestamp);
		allTables.add(ALERT_REFS);
		// =======================================================================================
		// ALERT_SPECIES
		// =======================================================================================
		ALERT_SPECIES = newTable("ALERT_SPECIES");
		ALERT_SPECIES__ALERT_ID = ALERT_SPECIES.addColumn("ALERT_ID", false,
				FieldType.Integer);
		ALERT_SPECIES__SPECIES_ID = ALERT_SPECIES.addColumn("SPECIES_ID",
				false, FieldType.Integer);
		ALERT_SPECIES__CREATION_DATE = ALERT_SPECIES.addColumn("CREATION_DATE",
				false, FieldType.Timestamp);
		allTables.add(ALERT_SPECIES);
		// =======================================================================================
		// ALERTS
		// =======================================================================================
		ALERTS = newTable("ALERTS");
		ALERTS__ALERT_ID = ALERTS.addKeyColumn("ALERT_ID", false,
				FieldType.Integer);
		ALERTS__ALERT_NAME = ALERTS.addColumn("ALERT_NAME", false,
				FieldType.String);
		ALERTS__ALERT_DESCRIPTION = ALERTS.addColumn("ALERT_DESCRIPTION",
				false, FieldType.Bytes);
		ALERTS__COMMENTS = ALERTS
				.addColumn("COMMENTS", false, FieldType.String);
		ALERTS__VIEWED_NUMBER = ALERTS.addColumn("VIEWED_NUMBER", false,
				FieldType.String);
		ALERTS__ACTIVE = ALERTS.addColumn("ACTIVE", false, FieldType.Boolean);
		ALERTS__RULEWRITER_COMMENTS = ALERTS.addColumn("RULEWRITER_COMMENTS",
				false, FieldType.String);
		ALERTS__RULEWRITER_ID = ALERTS.addColumn("RULEWRITER_ID", false,
				FieldType.String);
		ALERTS__CREATION_DATE = ALERTS.addColumn("CREATION_DATE", false,
				FieldType.Timestamp);
		ALERTS__LAST_MODIFIED_DATE = ALERTS.addColumn("LAST_MODIFIED_DATE",
				false, FieldType.Timestamp);
		ALERTS__EXPOSURE_ROUTE_ID = ALERTS.addColumn("EXPOSURE_ROUTE_ID",
				false, FieldType.Integer);
		ALERTS__DISTRIBUTION_ID = ALERTS.addColumn("DISTRIBUTION_ID", false,
				FieldType.Integer);
		ALERTS__PHASE_ID = ALERTS.addColumn("PHASE_ID", false,
				FieldType.Integer);
		ALERTS__CONFIDENCE_ID = ALERTS.addColumn("CONFIDENCE_ID", false,
				FieldType.Integer);
		allTables.add(ALERTS);
		// =======================================================================================
		// ASSAY
		// =======================================================================================
		ASSAY = newTable("ASSAY");
		ASSAY__ASSAY_ID = ASSAY.addKeyColumn("ASSAY_ID", false,
				FieldType.Integer);
		ASSAY__ASSAY = ASSAY.addColumn("ASSAY", false, FieldType.String);
		ASSAY__CREATION_DATE = ASSAY.addColumn("CREATION_DATE", false,
				FieldType.Timestamp);
		allTables.add(ASSAY);
		// =======================================================================================
		// ASSAY_DATA
		// =======================================================================================
		ASSAY_DATA = newTable("ASSAY_DATA");
		ASSAY_DATA__ASSAY_DATA_ID = ASSAY_DATA.addKeyColumn("ASSAY_DATA_ID",
				false, FieldType.Integer);
		ASSAY_DATA__BIO_ID = ASSAY_DATA.addColumn("BIO_ID", false,
				FieldType.Integer);
		ASSAY_DATA__ASSAY_SOURCE = ASSAY_DATA.addColumn("ASSAY_SOURCE", false,
				FieldType.String);
		ASSAY_DATA__ASSAY_TIME = ASSAY_DATA.addColumn("ASSAY_TIME", false,
				FieldType.String);
		ASSAY_DATA__QUANTIFICATION_OF_RESULTS = ASSAY_DATA.addColumn(
				"QUANTIFICATION_OF_RESULTS", false, FieldType.String);
		ASSAY_DATA__CHEM_CHARAC_PARAMS = ASSAY_DATA.addColumn(
				"CHEM_CHARAC_PARAMS", false, FieldType.String);
		ASSAY_DATA__CREATION_DATE = ASSAY_DATA.addColumn("CREATION_DATE",
				false, FieldType.Timestamp);
		allTables.add(ASSAY_DATA);
		// =======================================================================================
		// BIODATA
		// =======================================================================================
		BIODATA = newTable("BIODATA");
		BIODATA__BIO_ID = BIODATA.addKeyColumn("BIO_ID", false,
				FieldType.Integer);
		BIODATA__EXAMPLE_ID = BIODATA.addColumn("EXAMPLE_ID", false,
				FieldType.Integer);
		BIODATA__SPECIES_ID = BIODATA.addColumn("SPECIES_ID", false,
				FieldType.Integer);
		BIODATA__ASSAY_ID = BIODATA.addColumn("ASSAY_ID", false,
				FieldType.Integer);
		BIODATA__DATA = BIODATA.addColumn("DATA", false, FieldType.String);
		BIODATA__IN_VITRO = BIODATA.addColumn("IN_VITRO", false,
				FieldType.Boolean);
		BIODATA__ROUTE_OF_ADMIN = BIODATA.addColumn("ROUTE_OF_ADMIN", false,
				FieldType.String);
		BIODATA__DOSE_LEVELS = BIODATA.addColumn("DOSE_LEVELS", false,
				FieldType.String);
		BIODATA__SINGLE_DOSE = BIODATA.addColumn("SINGLE_DOSE", false,
				FieldType.Boolean);
		BIODATA__MULTI_DOSE = BIODATA.addColumn("MULTI_DOSE", false,
				FieldType.Boolean);
		BIODATA__DOSE_FREQUENCY = BIODATA.addColumn("DOSE_FREQUENCY", false,
				FieldType.String);
		BIODATA__TEST_SYSTEM = BIODATA.addColumn("TEST_SYSTEM", false,
				FieldType.Integer);
		BIODATA__CREATION_DATE = BIODATA.addColumn("CREATION_DATE", false,
				FieldType.Timestamp);
		allTables.add(BIODATA);
		// =======================================================================================
		// BIODATA_REFS
		// =======================================================================================
		BIODATA_REFS = newTable("BIODATA_REFS");
		BIODATA_REFS__REF_ID = BIODATA_REFS.addColumn("REF_ID", false,
				FieldType.Integer);
		BIODATA_REFS__BIO_ID = BIODATA_REFS.addColumn("BIO_ID", false,
				FieldType.Integer);
		BIODATA_REFS__CREATION_DATE = BIODATA_REFS.addColumn("CREATION_DATE",
				false, FieldType.Timestamp);
		allTables.add(BIODATA_REFS);
		// =======================================================================================
		// COMPONENT
		// =======================================================================================
		COMPONENT = newTable("COMPONENT");
		COMPONENT__Component_ID = COMPONENT.addKeyColumn("Component_ID", false,
				FieldType.Integer);
		COMPONENT__Pattern_ID = COMPONENT.addColumn("Pattern_ID", false,
				FieldType.Integer);
		COMPONENT__Stage_Number = COMPONENT.addColumn("Stage_Number", false,
				FieldType.Integer);
		COMPONENT__Sequence = COMPONENT.addColumn("Sequence", false,
				FieldType.Integer);
		COMPONENT__Creation_Date = COMPONENT.addColumn("Creation_Date", false,
				FieldType.Timestamp);
		allTables.add(COMPONENT);
		// =======================================================================================
		// COMPONENT_INTERMEDIATE
		// =======================================================================================
		COMPONENT_INTERMEDIATE = newTable("COMPONENT_INTERMEDIATE");
		COMPONENT_INTERMEDIATE__Component_ID = COMPONENT_INTERMEDIATE
				.addColumn("Component_ID", false, FieldType.Integer);
		COMPONENT_INTERMEDIATE__Intermediate_ID = COMPONENT_INTERMEDIATE
				.addColumn("Intermediate_ID", false, FieldType.Integer);
		COMPONENT_INTERMEDIATE__Creation_Date = COMPONENT_INTERMEDIATE
				.addColumn("Creation_Date", false, FieldType.Timestamp);
		allTables.add(COMPONENT_INTERMEDIATE);
		// =======================================================================================
		// DATABASE_DATES
		// =======================================================================================
		DATABASE_DATES = newTable("DATABASE_DATES");
		DATABASE_DATES__CREATION_DATE = DATABASE_DATES.addColumn(
				"CREATION_DATE", false, FieldType.Timestamp);
		allTables.add(DATABASE_DATES);
		// =======================================================================================
		// ENTITIES
		// =======================================================================================
		ENTITIES = newTable("ENTITIES");
		ENTITIES__ENTITY_ID = ENTITIES.addColumn("ENTITY_ID", false,
				FieldType.Integer);
		ENTITIES__ENTITY_DESCRIPTION = ENTITIES.addColumn("ENTITY_DESCRIPTION",
				false, FieldType.String);
		ENTITIES__CREATION_DATE = ENTITIES.addColumn("CREATION_DATE", false,
				FieldType.Timestamp);
		allTables.add(ENTITIES);
		// =======================================================================================
		// ENZYME
		// =======================================================================================
		ENZYME = newTable("ENZYME");
		ENZYME__ENZYME_ID = ENZYME.addKeyColumn("ENZYME_ID", false,
				FieldType.Integer);
		ENZYME__ENZYME = ENZYME.addColumn("ENZYME", false, FieldType.String);
		ENZYME__CREATION_DATE = ENZYME.addColumn("CREATION_DATE", false,
				FieldType.Timestamp);
		allTables.add(ENZYME);
		// =======================================================================================
		// EXAMPLE_PATTERNS
		// =======================================================================================
		EXAMPLE_PATTERNS = newTable("EXAMPLE_PATTERNS");
		EXAMPLE_PATTERNS__EXAMPLE_ID = EXAMPLE_PATTERNS.addColumn("EXAMPLE_ID",
				false, FieldType.Integer);
		EXAMPLE_PATTERNS__PATTERN_ID = EXAMPLE_PATTERNS.addColumn("PATTERN_ID",
				false, FieldType.Integer);
		EXAMPLE_PATTERNS__REACTION_STAGE = EXAMPLE_PATTERNS.addColumn(
				"REACTION_STAGE", false, FieldType.Integer);
		EXAMPLE_PATTERNS__CREATION_DATE = EXAMPLE_PATTERNS.addColumn(
				"CREATION_DATE", false, FieldType.Timestamp);
		allTables.add(EXAMPLE_PATTERNS);
		// =======================================================================================
		// EXAMPLES
		// =======================================================================================
		EXAMPLES = newTable("EXAMPLES");
		EXAMPLES__EXAMPLE_ID = EXAMPLES.addKeyColumn("EXAMPLE_ID", false,
				FieldType.Integer);
		EXAMPLES__EXAMPLE_NAME = EXAMPLES.addColumn("EXAMPLE_NAME", false,
				FieldType.String);
		EXAMPLES__CAS_NO = EXAMPLES
				.addColumn("CAS_NO", false, FieldType.String);
		EXAMPLES__EXAMPLE_PIC = EXAMPLES.addColumn("EXAMPLE_PIC", false,
				FieldType.Bytes);
		EXAMPLES__COMMENTS = EXAMPLES.addColumn("COMMENTS", false,
				FieldType.String);
		EXAMPLES__ACTIVE = EXAMPLES.addColumn("ACTIVE", false,
				FieldType.Boolean);
		EXAMPLES__PATTERN_ID = EXAMPLES.addColumn("PATTERN_ID", false,
				FieldType.Integer);
		EXAMPLES__RULEWRITER_COMMENTS = EXAMPLES.addColumn(
				"RULEWRITER_COMMENTS", false, FieldType.String);
		EXAMPLES__CREATION_DATE = EXAMPLES.addColumn("CREATION_DATE", false,
				FieldType.Timestamp);
		allTables.add(EXAMPLES);
		// =======================================================================================
		// EXPOSURE_ROUTES
		// =======================================================================================
		EXPOSURE_ROUTES = newTable("EXPOSURE_ROUTES");
		EXPOSURE_ROUTES__EXPOSURE_ROUTE_ID = EXPOSURE_ROUTES.addKeyColumn(
				"EXPOSURE_ROUTE_ID", false, FieldType.Integer);
		EXPOSURE_ROUTES__ROUTE_OF_EXPOSURE = EXPOSURE_ROUTES.addColumn(
				"ROUTE_OF_EXPOSURE", false, FieldType.String);
		EXPOSURE_ROUTES__CREATION_DATE = EXPOSURE_ROUTES.addColumn(
				"CREATION_DATE", false, FieldType.Timestamp);
		allTables.add(EXPOSURE_ROUTES);
		// =======================================================================================
		// EXTERNAL_EXAMPLES
		// =======================================================================================
		EXTERNAL_EXAMPLES = newTable("EXTERNAL_EXAMPLES");
		EXTERNAL_EXAMPLES__EXTERNAL_EXAMPLE_ID = EXTERNAL_EXAMPLES
				.addKeyColumn("EXTERNAL_EXAMPLE_ID", false, FieldType.Integer);
		EXTERNAL_EXAMPLES__ALERT_ID = EXTERNAL_EXAMPLES.addColumn("ALERT_ID",
				false, FieldType.Integer);
		EXTERNAL_EXAMPLES__EXTERNAL_PRODUCT_NAME = EXTERNAL_EXAMPLES.addColumn(
				"EXTERNAL_PRODUCT_NAME", false, FieldType.String);
		EXTERNAL_EXAMPLES__CREATION_DATE = EXTERNAL_EXAMPLES.addColumn(
				"CREATION_DATE", false, FieldType.Timestamp);
		allTables.add(EXTERNAL_EXAMPLES);
		// =======================================================================================
		// FORCE
		// =======================================================================================
		FORCE = newTable("FORCE");
		FORCE__Force_ID = FORCE.addKeyColumn("Force_ID", false,
				FieldType.Integer);
		FORCE__Force = FORCE.addColumn("Force", false, FieldType.String);
		FORCE__CREATION_DATE = FORCE.addColumn("CREATION_DATE", false,
				FieldType.Timestamp);
		allTables.add(FORCE);
		// =======================================================================================
		// GROUNDS_THRESHOLD
		// =======================================================================================
		GROUNDS_THRESHOLD = newTable("GROUNDS_THRESHOLD");
		GROUNDS_THRESHOLD__RULE_ID = GROUNDS_THRESHOLD.addColumn("RULE_ID",
				false, FieldType.Integer);
		GROUNDS_THRESHOLD__COUNTER = GROUNDS_THRESHOLD.addColumn("COUNTER",
				false, FieldType.Integer);
		GROUNDS_THRESHOLD__GROUNDS_ENTITY_ID = GROUNDS_THRESHOLD.addColumn(
				"GROUNDS_ENTITY_ID", false, FieldType.Integer);
		GROUNDS_THRESHOLD__THRESHOLD_ENTITY_ID = GROUNDS_THRESHOLD.addColumn(
				"THRESHOLD_ENTITY_ID", false, FieldType.Integer);
		GROUNDS_THRESHOLD__CREATION_DATE = GROUNDS_THRESHOLD.addColumn(
				"CREATION_DATE", false, FieldType.Timestamp);
		allTables.add(GROUNDS_THRESHOLD);
		// =======================================================================================
		// INTERMEDIATES
		// =======================================================================================
		INTERMEDIATES = newTable("INTERMEDIATES");
		INTERMEDIATES__ID = INTERMEDIATES.addColumn("ID", false,
				FieldType.Integer);
		INTERMEDIATES__Title = INTERMEDIATES.addColumn("Title", false,
				FieldType.String);
		INTERMEDIATES__Prefix = INTERMEDIATES.addColumn("Prefix", false,
				FieldType.String);
		INTERMEDIATES__Creation_Date = INTERMEDIATES.addColumn("Creation_Date",
				false, FieldType.Timestamp);
		allTables.add(INTERMEDIATES);
		// =======================================================================================
		// LEVELS_OF_CONFIDENCE
		// =======================================================================================
		LEVELS_OF_CONFIDENCE = newTable("LEVELS_OF_CONFIDENCE");
		LEVELS_OF_CONFIDENCE__CONFIDENCE_ID = LEVELS_OF_CONFIDENCE.addColumn(
				"CONFIDENCE_ID", false, FieldType.Integer);
		LEVELS_OF_CONFIDENCE__CONFIDENCE_LEVEL = LEVELS_OF_CONFIDENCE
				.addColumn("CONFIDENCE_LEVEL", false, FieldType.String);
		LEVELS_OF_CONFIDENCE__CREATION_DATE = LEVELS_OF_CONFIDENCE.addColumn(
				"CREATION_DATE", false, FieldType.Timestamp);
		allTables.add(LEVELS_OF_CONFIDENCE);
		// =======================================================================================
		// ORGAN_ENZYME
		// =======================================================================================
		ORGAN_ENZYME = newTable("ORGAN_ENZYME");
		ORGAN_ENZYME__ORGAN_ID = ORGAN_ENZYME.addColumn("ORGAN_ID", false,
				FieldType.Integer);
		ORGAN_ENZYME__ENZYME_ID = ORGAN_ENZYME.addColumn("ENZYME_ID", false,
				FieldType.Integer);
		ORGAN_ENZYME__CREATION_DATE = ORGAN_ENZYME.addColumn("CREATION_DATE",
				false, FieldType.Timestamp);
		allTables.add(ORGAN_ENZYME);
		// =======================================================================================
		// ORGAN_EXPOSURE
		// =======================================================================================
		ORGAN_EXPOSURE = newTable("ORGAN_EXPOSURE");
		ORGAN_EXPOSURE__ORGAN_ID = ORGAN_EXPOSURE.addColumn("ORGAN_ID", false,
				FieldType.Integer);
		ORGAN_EXPOSURE__EXPOSURE_ROUTE_ID = ORGAN_EXPOSURE.addColumn(
				"EXPOSURE_ROUTE_ID", false, FieldType.Integer);
		ORGAN_EXPOSURE__CREATION_DATE = ORGAN_EXPOSURE.addColumn(
				"CREATION_DATE", false, FieldType.Timestamp);
		allTables.add(ORGAN_EXPOSURE);
		// =======================================================================================
		// ORGANS
		// =======================================================================================
		ORGANS = newTable("ORGANS");
		ORGANS__ORGAN_ID = ORGANS.addKeyColumn("ORGAN_ID", false,
				FieldType.Integer);
		ORGANS__TARGET_ORGAN = ORGANS.addColumn("TARGET_ORGAN", false,
				FieldType.String);
		ORGANS__CREATION_DATE = ORGANS.addColumn("CREATION_DATE", false,
				FieldType.Timestamp);
		allTables.add(ORGANS);
		// =======================================================================================
		// PATTERNS
		// =======================================================================================
		PATTERNS = newTable("PATTERNS");
		PATTERNS__PATTERN_ID = PATTERNS.addKeyColumn("PATTERN_ID", false,
				FieldType.Integer);
		PATTERNS__ALERT_ID = PATTERNS.addColumn("ALERT_ID", false,
				FieldType.Integer);
		PATTERNS__PATTERN = PATTERNS.addColumn("PATTERN", false,
				FieldType.Bytes);
		PATTERNS__INCLUSION_PATTERN = PATTERNS.addColumn("INCLUSION_PATTERN",
				false, FieldType.Short);
		PATTERNS__PATTERN_KEY = PATTERNS.addColumn("PATTERN_KEY", false,
				FieldType.Integer);
		PATTERNS__PATTERN_KEY2 = PATTERNS.addColumn("PATTERN_KEY2", false,
				FieldType.Integer);
		PATTERNS__PATTERN_KEY3 = PATTERNS.addColumn("PATTERN_KEY3", false,
				FieldType.Integer);
		PATTERNS__CREATION_DATE = PATTERNS.addColumn("CREATION_DATE", false,
				FieldType.Timestamp);
		allTables.add(PATTERNS);
		// =======================================================================================
		// PHASE
		// =======================================================================================
		PHASE = newTable("PHASE");
		PHASE__PHASE_ID = PHASE.addKeyColumn("PHASE_ID", false,
				FieldType.Integer);
		PHASE__PHASE_EXPRESSION = PHASE.addColumn("PHASE_EXPRESSION", false,
				FieldType.String);
		PHASE__CREATION_DATE = PHASE.addColumn("CREATION_DATE", false,
				FieldType.Timestamp);
		allTables.add(PHASE);
		// =======================================================================================
		// PHYSCHEM
		// =======================================================================================
		PHYSCHEM = newTable("PHYSCHEM");
		PHYSCHEM__PhysChem_ID = PHYSCHEM.addKeyColumn("PhysChem_ID", false,
				FieldType.Integer);
		PHYSCHEM__Name = PHYSCHEM.addColumn("Name", false, FieldType.String);
		PHYSCHEM__Comment = PHYSCHEM.addColumn("Comment", false,
				FieldType.String);
		PHYSCHEM__ReturnType = PHYSCHEM.addColumn("ReturnType", false,
				FieldType.String);
		PHYSCHEM__CREATION_DATE = PHYSCHEM.addColumn("CREATION_DATE", false,
				FieldType.Timestamp);
		allTables.add(PHYSCHEM);
		// =======================================================================================
		// PRODUCT_DISTRIBUTION
		// =======================================================================================
		PRODUCT_DISTRIBUTION = newTable("PRODUCT_DISTRIBUTION");
		PRODUCT_DISTRIBUTION__DISTRIBUTION_ID = PRODUCT_DISTRIBUTION.addColumn(
				"DISTRIBUTION_ID", false, FieldType.Integer);
		PRODUCT_DISTRIBUTION__DISTRIBUTION_EXPRESSION = PRODUCT_DISTRIBUTION
				.addColumn("DISTRIBUTION_EXPRESSION", false, FieldType.String);
		PRODUCT_DISTRIBUTION__CREATION_DATE = PRODUCT_DISTRIBUTION.addColumn(
				"CREATION_DATE", false, FieldType.Timestamp);
		allTables.add(PRODUCT_DISTRIBUTION);
		// =======================================================================================
		// REFERENCES
		// =======================================================================================
		REFERENCES = newTable("REFERENCES");
		REFERENCES__REF_ID = REFERENCES.addKeyColumn("REF_ID", false,
				FieldType.Integer);
		REFERENCES__TITLE = REFERENCES.addColumn("TITLE", false,
				FieldType.String);
		REFERENCES__AUTHORS = REFERENCES.addColumn("AUTHORS", false,
				FieldType.String);
		REFERENCES__VOLUME = REFERENCES.addColumn("VOLUME", false,
				FieldType.String);
		REFERENCES__PAGES = REFERENCES.addColumn("PAGES", false,
				FieldType.String);
		REFERENCES__YEAR = REFERENCES
				.addColumn("YEAR", false, FieldType.String);
		REFERENCES__SOURCE_ID = REFERENCES.addColumn("SOURCE_ID", false,
				FieldType.Integer);
		REFERENCES__CREATION_DATE = REFERENCES.addColumn("CREATION_DATE",
				false, FieldType.Timestamp);
		allTables.add(REFERENCES);
		// =======================================================================================
		// RELATIVE
		// =======================================================================================
		RELATIVE = newTable("RELATIVE");
		RELATIVE__RELATIVE_ID = RELATIVE.addKeyColumn("RELATIVE_ID", false,
				FieldType.Integer);
		RELATIVE__VALUE1 = RELATIVE.addColumn("VALUE1", false,
				FieldType.Integer);
		RELATIVE__OPERATOR = RELATIVE.addColumn("OPERATOR", false,
				FieldType.String);
		RELATIVE__VALUE2 = RELATIVE.addColumn("VALUE2", false,
				FieldType.Integer);
		RELATIVE__ACTIVE = RELATIVE.addColumn("ACTIVE", false,
				FieldType.Boolean);
		RELATIVE__NAME = RELATIVE.addColumn("NAME", false, FieldType.String);
		RELATIVE__COMMENT = RELATIVE.addColumn("COMMENT", false,
				FieldType.String);
		RELATIVE__RULEWRITER_COMMENT = RELATIVE.addColumn("RULEWRITER_COMMENT",
				false, FieldType.String);
		RELATIVE__CREATION_DATE = RELATIVE.addColumn("CREATION_DATE", false,
				FieldType.Timestamp);
		allTables.add(RELATIVE);
		// =======================================================================================
		// RELATIVE_REF
		// =======================================================================================
		RELATIVE_REF = newTable("RELATIVE_REF");
		RELATIVE_REF__RELATIVE_ID = RELATIVE_REF.addColumn("RELATIVE_ID",
				false, FieldType.Integer);
		RELATIVE_REF__REF_ID = RELATIVE_REF.addColumn("REF_ID", false,
				FieldType.Integer);
		RELATIVE_REF__CREATION_DATE = RELATIVE_REF.addColumn("CREATION_DATE",
				false, FieldType.Timestamp);
		allTables.add(RELATIVE_REF);
		// =======================================================================================
		// RULE_REF
		// =======================================================================================
		RULE_REF = newTable("RULE_REF");
		RULE_REF__RULE_ID = RULE_REF.addColumn("RULE_ID", false,
				FieldType.Integer);
		RULE_REF__REF_ID = RULE_REF.addColumn("REF_ID", false,
				FieldType.Integer);
		RULE_REF__CREATION_DATE = RULE_REF.addColumn("CREATION_DATE", false,
				FieldType.Timestamp);
		allTables.add(RULE_REF);
		// =======================================================================================
		// RULES
		// =======================================================================================
		RULES = newTable("RULES");
		RULES__RULE_ID = RULES
				.addKeyColumn("RULE_ID", false, FieldType.Integer);
		RULES__RULE_NAME = RULES
				.addColumn("RULE_NAME", false, FieldType.String);
		RULES__PROPOSITION_ENTITY_ID = RULES.addColumn("PROPOSITION_ENTITY_ID",
				false, FieldType.Integer);
		RULES__FORCE_ENTITY_ID = RULES.addColumn("FORCE_ENTITY_ID", false,
				FieldType.Integer);
		RULES__RULE_ACTIVE = RULES.addColumn("RULE_ACTIVE", false,
				FieldType.Boolean);
		RULES__RULE_FAMILY = RULES.addColumn("RULE_FAMILY", false,
				FieldType.Integer);
		RULES__COMMENTS = RULES.addColumn("COMMENTS", false, FieldType.String);
		RULES__RULEWRITERS_COMMENTS = RULES.addColumn("RULEWRITERS_COMMENTS",
				false, FieldType.String);
		RULES__CREATION_DATE = RULES.addColumn("CREATION_DATE", false,
				FieldType.Timestamp);
		allTables.add(RULES);
		// =======================================================================================
		// SOURCE_REF
		// =======================================================================================
		SOURCE_REF = newTable("SOURCE_REF");
		SOURCE_REF__SOURCE_ID = SOURCE_REF.addColumn("SOURCE_ID", false,
				FieldType.Integer);
		SOURCE_REF__SOURCE_TITLE = SOURCE_REF.addColumn("SOURCE_TITLE", false,
				FieldType.String);
		SOURCE_REF__SOURCE_TYPE = SOURCE_REF.addColumn("SOURCE_TYPE", false,
				FieldType.String);
		SOURCE_REF__CREATION_DATE = SOURCE_REF.addColumn("CREATION_DATE",
				false, FieldType.Timestamp);
		allTables.add(SOURCE_REF);
		// =======================================================================================
		// SPECIES
		// =======================================================================================
		SPECIES = newTable("SPECIES");
		SPECIES__SPECIES_ID = SPECIES.addKeyColumn("SPECIES_ID", false,
				FieldType.Integer);
		SPECIES__SPECIES = SPECIES
				.addColumn("SPECIES", false, FieldType.String);
		SPECIES__CREATION_DATE = SPECIES.addColumn("CREATION_DATE", false,
				FieldType.Timestamp);
		allTables.add(SPECIES);
		// =======================================================================================
		// SPECIES_ENZYME
		// =======================================================================================
		SPECIES_ENZYME = newTable("SPECIES_ENZYME");
		SPECIES_ENZYME__SPECIES_ID = SPECIES_ENZYME.addColumn("SPECIES_ID",
				false, FieldType.Integer);
		SPECIES_ENZYME__ENZYME_ID = SPECIES_ENZYME.addColumn("ENZYME_ID",
				false, FieldType.Integer);
		SPECIES_ENZYME__CREATION_DATE = SPECIES_ENZYME.addColumn(
				"CREATION_DATE", false, FieldType.Timestamp);
		allTables.add(SPECIES_ENZYME);
		// =======================================================================================
		// SPECIES_ORGAN
		// =======================================================================================
		SPECIES_ORGAN = newTable("SPECIES_ORGAN");
		SPECIES_ORGAN__SPECIES_ID = SPECIES_ORGAN.addColumn("SPECIES_ID",
				false, FieldType.Integer);
		SPECIES_ORGAN__ORGAN_ID = SPECIES_ORGAN.addColumn("ORGAN_ID", false,
				FieldType.Integer);
		SPECIES_ORGAN__CREATION_DATE = SPECIES_ORGAN.addColumn("CREATION_DATE",
				false, FieldType.Timestamp);
		allTables.add(SPECIES_ORGAN);
		// =======================================================================================
		// SUPPLEMENTAL
		// =======================================================================================
		SUPPLEMENTAL = newTable("SUPPLEMENTAL");
		SUPPLEMENTAL__REF_ID = SUPPLEMENTAL.addColumn("REF_ID", false,
				FieldType.Integer);
		SUPPLEMENTAL__SUPP_DATA = SUPPLEMENTAL.addColumn("SUPP_DATA", false,
				FieldType.String);
		SUPPLEMENTAL__CREATION_DATE = SUPPLEMENTAL.addColumn("CREATION_DATE",
				false, FieldType.Timestamp);
		allTables.add(SUPPLEMENTAL);
		// =======================================================================================
		// TAXONS
		// =======================================================================================
		TAXONS = newTable("TAXONS");
		TAXONS__SPECIES_ID = TAXONS.addColumn("SPECIES_ID", false,
				FieldType.Integer);
		TAXONS__HIGHER_TAXON_ID = TAXONS.addColumn("HIGHER_TAXON_ID", false,
				FieldType.Integer);
		TAXONS__CREATION_DATE = TAXONS.addColumn("CREATION_DATE", false,
				FieldType.Timestamp);
		allTables.add(TAXONS);
		// =======================================================================================
		// TEMPLATES
		// =======================================================================================
		TEMPLATES = newTable("TEMPLATES");
		TEMPLATES__template_id = TEMPLATES.addKeyColumn("template_id", false,
				FieldType.Integer);
		TEMPLATES__template_name = TEMPLATES.addColumn("template_name", false,
				FieldType.String);
		TEMPLATES__template_string = TEMPLATES.addColumn("template_string",
				false, FieldType.Bytes);
		TEMPLATES__CREATION_DATE = TEMPLATES.addColumn("CREATION_DATE", false,
				FieldType.Timestamp);
		allTables.add(TEMPLATES);

		makeJoins();
		tables = ImmutableList.copyOf(allTables);
	}

	/** 
	 *
	 */
	private void makeJoins() {

		// =======================================================================================
		// JOIN TABLE ALERT_EXAMPLES
		// =======================================================================================
		ALERT_EXAMPLES__ALERT_ID.fkReferenceTo(ALERTS__ALERT_ID);

		ALERT_EXAMPLES__EXAMPLE_ID.fkReferenceTo(EXAMPLES__EXAMPLE_ID);

		// =======================================================================================
		// JOIN TABLE ALERT_ORGAN
		// =======================================================================================
		ALERT_ORGAN__ALERT_ID.fkReferenceTo(ALERTS__ALERT_ID);

		ALERT_ORGAN__ORGAN_ID.fkReferenceTo(ORGANS__ORGAN_ID);

		// =======================================================================================
		// JOIN TABLE ALERT_REFS
		// =======================================================================================
		ALERT_REFS__ALERT_ID.fkReferenceTo(ALERTS__ALERT_ID);

		ALERT_REFS__REF_ID.fkReferenceTo(REFERENCES__REF_ID);

		// =======================================================================================
		// JOIN TABLE ALERT_SPECIES
		// =======================================================================================
		ALERT_SPECIES__ALERT_ID.fkReferenceTo(ALERTS__ALERT_ID);

		ALERT_SPECIES__SPECIES_ID.fkReferenceTo(SPECIES__SPECIES_ID);

		// =======================================================================================
		// ALERTS
		// =======================================================================================
		ALERTS__EXPOSURE_ROUTE_ID
				.fkReferenceTo(EXPOSURE_ROUTES__EXPOSURE_ROUTE_ID);

		ALERTS__PHASE_ID.fkReferenceTo(PHASE__PHASE_ID);

		// =======================================================================================
		// ASSAY_DATA
		// =======================================================================================
		ASSAY_DATA__BIO_ID.fkReferenceTo(BIODATA__BIO_ID);

		// =======================================================================================
		// BIODATA
		// =======================================================================================
		BIODATA__EXAMPLE_ID.fkReferenceTo(EXAMPLES__EXAMPLE_ID);

		BIODATA__SPECIES_ID.fkReferenceTo(SPECIES__SPECIES_ID);

		BIODATA__ASSAY_ID.fkReferenceTo(ASSAY__ASSAY_ID);

		// =======================================================================================
		// JOIN TABLE BIODATA_REFS
		// =======================================================================================
		BIODATA_REFS__REF_ID.fkReferenceTo(REFERENCES__REF_ID);

		BIODATA_REFS__BIO_ID.fkReferenceTo(BIODATA__BIO_ID);

		// =======================================================================================
		// COMPONENT
		// =======================================================================================
		COMPONENT__Pattern_ID.fkReferenceTo(PATTERNS__PATTERN_ID);

		// =======================================================================================
		// COMPONENT_INTERMEDIATE
		// =======================================================================================
		COMPONENT_INTERMEDIATE__Component_ID
				.fkReferenceTo(COMPONENT__Component_ID);

		// =======================================================================================
		// EXAMPLE_PATTERNS
		// =======================================================================================
		EXAMPLE_PATTERNS__EXAMPLE_ID.fkReferenceTo(EXAMPLES__EXAMPLE_ID);

		EXAMPLE_PATTERNS__PATTERN_ID.fkReferenceTo(PATTERNS__PATTERN_ID);

		// =======================================================================================
		// EXAMPLES
		// =======================================================================================
		EXAMPLES__PATTERN_ID.fkReferenceTo(PATTERNS__PATTERN_ID);

		// =======================================================================================
		// EXTERNAL_EXAMPLES
		// =======================================================================================
		EXTERNAL_EXAMPLES__ALERT_ID.fkReferenceTo(ALERTS__ALERT_ID);

		// =======================================================================================
		// GROUNDS_THRESHOLD
		// =======================================================================================
		GROUNDS_THRESHOLD__RULE_ID.fkReferenceTo(RULES__RULE_ID);

		// =======================================================================================
		// JOIN TABLE ORGAN_ENZYME
		// =======================================================================================
		ORGAN_ENZYME__ORGAN_ID.fkReferenceTo(ORGANS__ORGAN_ID);

		ORGAN_ENZYME__ENZYME_ID.fkReferenceTo(ENZYME__ENZYME_ID);

		// =======================================================================================
		// JOIN TABLE ORGAN_EXPOSURE
		// =======================================================================================
		ORGAN_EXPOSURE__ORGAN_ID.fkReferenceTo(ORGANS__ORGAN_ID);

		ORGAN_EXPOSURE__EXPOSURE_ROUTE_ID
				.fkReferenceTo(EXPOSURE_ROUTES__EXPOSURE_ROUTE_ID);

		// =======================================================================================
		// PATTERNS
		// =======================================================================================
		PATTERNS__ALERT_ID.fkReferenceTo(ALERTS__ALERT_ID);

		// =======================================================================================
		// JOIN TABLE RELATIVE_REF
		// =======================================================================================
		RELATIVE_REF__RELATIVE_ID.fkReferenceTo(RELATIVE__RELATIVE_ID);

		RELATIVE_REF__REF_ID.fkReferenceTo(REFERENCES__REF_ID);

		// =======================================================================================
		// JOIN TABLE RULE_REF
		// =======================================================================================
		RULE_REF__RULE_ID.fkReferenceTo(RULES__RULE_ID);

		RULE_REF__REF_ID.fkReferenceTo(REFERENCES__REF_ID);

		// =======================================================================================
		// JOIN TABLE SPECIES_ENZYME
		// =======================================================================================
		SPECIES_ENZYME__SPECIES_ID.fkReferenceTo(SPECIES__SPECIES_ID);

		SPECIES_ENZYME__ENZYME_ID.fkReferenceTo(ENZYME__ENZYME_ID);

		// =======================================================================================
		// JOIN TABLE SPECIES_ORGAN
		// =======================================================================================
		SPECIES_ORGAN__SPECIES_ID.fkReferenceTo(SPECIES__SPECIES_ID);

		SPECIES_ORGAN__ORGAN_ID.fkReferenceTo(ORGANS__ORGAN_ID);

		// =======================================================================================
		// SUPPLEMENTAL
		// =======================================================================================
		SUPPLEMENTAL__REF_ID.fkReferenceTo(REFERENCES__REF_ID);

		// =======================================================================================
		// TAXONS
		// =======================================================================================
		TAXONS__SPECIES_ID.fkReferenceTo(SPECIES__SPECIES_ID);

	}
}
