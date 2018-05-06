package oscuroweb.ia.screen;

import com.jarektoro.responsivelayout.ResponsiveColumn.ColumnComponentAlignment;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToDoubleConverter;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import oscuroweb.ia.converter.EducationTypeConverter;
import oscuroweb.ia.converter.MaritalStatusTypeConverter;
import oscuroweb.ia.converter.NativeCountryTypeConverter;
import oscuroweb.ia.converter.OccupationTypeConverter;
import oscuroweb.ia.converter.RaceTypeConverter;
import oscuroweb.ia.converter.RelationshipTypeConverter;
import oscuroweb.ia.converter.SexTypeConverter;
import oscuroweb.ia.converter.WorkClassTypeConverter;
import oscuroweb.ia.dto.IncomeDto;
import oscuroweb.ia.dto.OutputDto;
import oscuroweb.ia.dto.OutputResultDto;
import oscuroweb.ia.type.EducationType;
import oscuroweb.ia.type.MaritalStatusType;
import oscuroweb.ia.type.NativeCountryType;
import oscuroweb.ia.type.OccupationType;
import oscuroweb.ia.type.RaceType;
import oscuroweb.ia.type.RelationshipType;
import oscuroweb.ia.type.SexType;
import oscuroweb.ia.type.WorkClassType;

@Getter @Setter
@Slf4j
@SpringView(name = IncomeInputScreen.VIEW_NAME)
public class IncomeInputScreen extends AbstractIncomeView implements View {

	public static final String VIEW_NAME = "";

	private static final long serialVersionUID = 1987326654373106728L;

	private TextField age;
	private TextField fnlwgt;
	private TextField capitalGain;
	private TextField capitalLoss;
	private TextField hoursPerWeek;
	private TextField educationNum;
	private Label lblResult;
	private Label lblTitle;
	private Button bnIncome;
	private Button bnYes;
	private Button bnNo;
	private ComboBox<SexType> sex;
	private ComboBox<RaceType> race;
	private ComboBox<RelationshipType> relationship;
	private ComboBox<OccupationType> occupation;
	private ComboBox<MaritalStatusType> maritalStatus;
	private ComboBox<EducationType> eduaction;
	private ComboBox<WorkClassType> workClass;
	private ComboBox<NativeCountryType> nativeCountry;
	
	private Binder<IncomeDto> binder;
	private IncomeDto income = IncomeDto.builder().build();
	
	private Window windowResult;

	private String result;

	protected void initView() {
		
		initializeTextFields();
		initializeCombos();
		initializeWindowResult();
		initializeBinder();
		// TODO Remove
		initializeForm();

		ResponsiveRow row = createRow();
		
		addColumns(row, 
				age,
				workClass,
				fnlwgt,
				eduaction,
				educationNum,
				maritalStatus,
				occupation,
				relationship,
				race,
				sex,
				capitalGain,
				capitalLoss,
				hoursPerWeek,
				nativeCountry);
		
		bnIncome = new Button("Get Income");
		bnIncome.setIcon(VaadinIcons.SEARCH);
		bnIncome.addStyleNames(ValoTheme.BUTTON_HUGE, ValoTheme.BUTTON_BORDERLESS);
		bnIncome.addClickListener(event -> onClickIncomeButton(event));
		
		ResponsiveRow btRow = createRow();
		btRow.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		
		addColumn(btRow, bnIncome);
	}
	
	private void initializeTextFields() {
		age = new TextField("Age:");
		fnlwgt = new TextField("Sampling weight:");
		capitalGain = new TextField("Capital Gain:");
		capitalLoss = new TextField("Capital Loss:");
		hoursPerWeek = new TextField("Hours Per Week:");
		educationNum = new TextField("Education Number:");
		educationNum.setEnabled(false);
	}
	
	private void initializeWindowResult() {
		lblTitle = new Label("Your income is");
		lblTitle.addStyleNames(ValoTheme.LABEL_H3, ValoTheme.LABEL_BOLD);
		lblResult = new Label();
		lblResult.addStyleNames(ValoTheme.LABEL_H3, ValoTheme.LABEL_COLORED, ValoTheme.LABEL_BOLD);
		
		bnYes = new Button("Yes");
		bnYes.addStyleNames(ValoTheme.BUTTON_FRIENDLY, ValoTheme.BUTTON_LARGE);
		bnYes.setIcon(VaadinIcons.CHECK_CIRCLE);
		bnYes.addClickListener(event -> onClickYesButton(event));
		
		bnNo = new Button("No");
		bnNo.addStyleNames(ValoTheme.BUTTON_DANGER, ValoTheme.BUTTON_LARGE);
		bnNo.setIcon(VaadinIcons.CLOSE_CIRCLE);
		bnNo.addClickListener(event -> onClickNoButton(event));
		

		ResponsiveLayout windowLayout = new ResponsiveLayout();
		
		ResponsiveRow resultRow = createRow(12, 12, 6, 6);
		resultRow.addColumn()
			.withVisibilityRules(false, false, true, true)
			.withComponent(lblTitle, ColumnComponentAlignment.RIGHT);
		resultRow.addColumn()
			.withComponent(lblResult, ColumnComponentAlignment.LEFT);
		windowLayout.addRow(resultRow);

		ResponsiveRow btRow = createRow(12, 12, 6, 6);	
		addColumns(btRow, bnYes, bnNo);
		windowLayout.addRow(btRow);
		
		windowResult = new Window("Income Prediction");
		windowResult.setClosable(true);
		windowResult.setWidth(50, Unit.PERCENTAGE);
		windowResult.setHeight(50, Unit.PERCENTAGE);
		windowResult.center();
		windowResult.setContent(windowLayout);
	}


	private void initializeCombos() {

		// Combo Sex
		sex = new ComboBox<SexType>("Sex:");
		sex.setItems(SexType.values());
		// Use the name property for item captions
		sex.setItemCaptionGenerator(SexType::desc);

		// Combo Race
		race = new ComboBox<RaceType>("Race:");
		race.setItems(RaceType.values());
		// Use the name property for item captions
		race.setItemCaptionGenerator(RaceType::desc);

		// Combo Relationship
		relationship = new ComboBox<RelationshipType>("Relationship:");
		relationship.setItems(RelationshipType.values());
		// Use the name property for item captions
		relationship.setItemCaptionGenerator(RelationshipType::desc);

		// Combo Occupation
		occupation = new ComboBox<OccupationType>("Occupation:");
		occupation.setItems(OccupationType.values());
		// Use the name property for item captions
		occupation.setItemCaptionGenerator(OccupationType::desc);

		// Combo Relationship
		maritalStatus = new ComboBox<MaritalStatusType>("Marital Status:");
		maritalStatus.setItems(MaritalStatusType.values());
		// Use the name property for item captions
		maritalStatus.setItemCaptionGenerator(MaritalStatusType::desc);

		// Combo workClass
		workClass = new ComboBox<WorkClassType>("WorkClass:");
		workClass.setItems(WorkClassType.values());
		// Use the name property for item captions
		workClass.setItemCaptionGenerator(WorkClassType::desc);

		// Combo Education
		eduaction = new ComboBox<EducationType>("Education:");
		eduaction.setItems(EducationType.values());
		// Use the name property for item captions
		eduaction.setItemCaptionGenerator(EducationType::desc);
		eduaction.addSelectionListener(selectedItem -> 
			selectedItem.getSelectedItem().ifPresent(item -> 
				educationNum.setValue(String.valueOf(item.num()))
			)
		);

		// Combo Native Country
		nativeCountry = new ComboBox<NativeCountryType>("Native Country:");
		nativeCountry.setItems(NativeCountryType.values());
		// Use the name property for item captions
		nativeCountry.setItemCaptionGenerator(NativeCountryType::desc);
	}
	
	private void initializeBinder() {
		income = IncomeDto.builder().build();
		binder = new Binder<IncomeDto>(IncomeDto.class);
		binder.forField(age)
				.asRequired()
				.withConverter(new StringToDoubleConverter("Must be enter a number"))
				.withValidator(ageValue -> ageValue > 0d && ageValue <= 120d, "More than 1 and less than 120")
				.bind(IncomeDto::getAge, IncomeDto::setAge);
		binder.forField(fnlwgt)
				.asRequired()
				.withConverter(new StringToDoubleConverter("Must be enter a number"))
				.bind(IncomeDto::getFnlwgt, IncomeDto::setFnlwgt);
		binder.forField(capitalGain)
				.asRequired()
				.withConverter(new StringToDoubleConverter("Must be enter a number"))
				.bind(IncomeDto::getCapitalGain, IncomeDto::setCapitalLoss);
		binder.forField(capitalLoss)
				.asRequired()
				.withConverter(new StringToDoubleConverter("Must be enter a number"))
				.bind(IncomeDto::getCapitalLoss, IncomeDto::setCapitalLoss);
		binder.forField(hoursPerWeek)
				.asRequired()
				.withConverter(new StringToDoubleConverter("Must be enter a number"))
				.withValidator(hours -> hours >= 0 && hours < 80, "More than 0 and less than 80")
				.bind(IncomeDto::getHoursPerWeek, IncomeDto::setHoursPerWeek );
		binder.forField(educationNum)
				.asRequired()
				.withConverter(new StringToDoubleConverter("Must be enter a number"))
				.bind(IncomeDto::getEducationNum, IncomeDto::setEducationNum );
		binder.forField(sex)
				.asRequired()
				.withConverter(new SexTypeConverter())
				.bind(IncomeDto::getSex, IncomeDto::setSex);
		binder.forField(race)
				.asRequired()
				.withConverter(new RaceTypeConverter())
				.bind(IncomeDto::getRace, IncomeDto::setRace);
		binder.forField(relationship)
				.asRequired()
				.withConverter(new RelationshipTypeConverter())
				.bind(IncomeDto::getRelationship, IncomeDto::setRelationship);
		binder.forField(occupation)
				.asRequired()
				.withConverter(new OccupationTypeConverter())
				.bind(IncomeDto::getOccupation, IncomeDto::setOccupation);
		binder.forField(maritalStatus)
				.asRequired()
				.withConverter(new MaritalStatusTypeConverter())
				.bind(IncomeDto::getMaritalStatus, IncomeDto::setMaritalStatus);
		binder.forField(eduaction)
				.asRequired()
				.withConverter(new EducationTypeConverter())
				.bind(IncomeDto::getEducation, IncomeDto::setEducation);
		binder.forField(workClass)
				.asRequired()
				.withConverter(new WorkClassTypeConverter())
				.bind(IncomeDto::getWorkclass, IncomeDto::setWorkclass);
		binder.forField(nativeCountry)
				.asRequired()
				.withConverter(new NativeCountryTypeConverter())
				.bind(IncomeDto::getNativeCountry, IncomeDto::setNativeCountry);

	}
	
	private void initializeForm() {
		income = IncomeDto.builder()
			.age(23d)
			.capitalGain(1000d)
			.capitalLoss(10d)
			.education(EducationType.ASSOC_ACDM.desc())
			.educationNum(EducationType.ASSOC_ACDM.num())
			.fnlwgt(1000d)
			.hoursPerWeek(40d)
			.maritalStatus(MaritalStatusType.DIVORCED.desc())
			.nativeCountry(NativeCountryType.CAMBODIA.desc())
			.occupation(OccupationType.ADM_CLERICAL.desc())
			.race(RaceType.AMER_INDIAN_ESKIMO.desc())
			.relationship(RelationshipType.HUSBAND.desc())
			.sex(SexType.FEMALE.desc())
			.workclass(WorkClassType.FEDERAL_GOV.desc())
			.build();

		binder.bindInstanceFields(income);
		binder.readBean(income);
	}

	private void onClickYesButton(ClickEvent event) {
		sendResults(true);
	}

	private void onClickNoButton(ClickEvent event) {
		sendResults(false);
	}

	private void onClickIncomeButton(ClickEvent event) {
		
		boolean valid =  binder.writeBeanIfValid(income);
		
		if (valid) {
			try {
				log.info("Income: {}", income.toString());
				OutputDto output = restclient.evaluateData(income);
				
				lblResult.setValue(output.getLabel());
				result = output.getLabel();
				UI.getCurrent().addWindow(windowResult);
			} catch (Exception e) {
				log.error("An error ocurred invoking the service: {}", e.getMessage(), e);
				Notification.show("An error ocurred invoking the service", Notification.Type.ERROR_MESSAGE);
			}
		} else {
			Notification.show("Invalid income", Notification.Type.ERROR_MESSAGE);	
		}
	}

	private void sendResults(boolean evalresult) {

		income.setSuccess(evalresult);

		OutputResultDto outputDto = restclient.addResult(income);
		
		if (outputDto != null && outputDto.getUpdated()) {
			Notification.show("Result sent", Notification.Type.HUMANIZED_MESSAGE);
		} else {
			Notification.show("An error ocurred", Notification.Type.WARNING_MESSAGE);		
		}
		
		windowResult.close();
	}

}
