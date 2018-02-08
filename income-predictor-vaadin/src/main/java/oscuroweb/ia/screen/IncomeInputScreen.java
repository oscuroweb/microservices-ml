package oscuroweb.ia.screen;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

import lombok.extern.slf4j.Slf4j;
import oscuroweb.ia.dto.ComboDto;
import oscuroweb.ia.dto.ComboEducationDto;
import oscuroweb.ia.dto.InputResultDto;
import oscuroweb.ia.dto.OutputDto;
import oscuroweb.ia.dto.OutputResultDto;
import oscuroweb.ia.rest.RestClient;

@Slf4j
@SpringView(name = IncomeInputScreen.VIEW_NAME)
public class IncomeInputScreen extends FormLayout implements View {

	public static final String VIEW_NAME = "";

	private static final long serialVersionUID = 1987326654373106728L;


	private TextField textAge, textFnlwgt,  textCapitalGain, textCapitalLoss, textHoursPerWeek ;

	private Label lblResult;
	private Button bnIncome, bnYes, bnNo;

	ComboBox<ComboDto> selectSex;
	ComboBox<ComboDto> selectRace;
	ComboBox<ComboDto> selectRelationship;
	ComboBox<ComboDto> selectOccupation;
	ComboBox<ComboDto> selectMaritalStatus;
	ComboBox<ComboEducationDto> selectEduaction;
	ComboBox<ComboDto> selectWorkClass;
	ComboBox<ComboDto> selectNativeCountry;

	HorizontalLayout horizontalLayout4, horizontalLayout5;

	@Autowired
	RestClient restclient;

	private String result;

	@PostConstruct
	public void init() {

		this.removeAllComponents();

		//Init Combo Vales
		initCombos();

		//Campos de la ventana
		textAge = new TextField("Age:");
		textFnlwgt = new TextField("Sampling weight:");
		textCapitalGain = new TextField("Capital Gain:");
		textCapitalLoss = new TextField("Capital Loss:");
		textHoursPerWeek = new TextField("Hours Per Week:");


		bnIncome = new Button("Get Income");
		bnIncome.addClickListener(event -> onClickIncomeButton(event));
		bnYes = new Button("Yes");
		bnYes.addClickListener(event -> onClickYesButton(event));
		bnNo = new Button("No");
		bnNo.addClickListener(event -> onClickNoButton(event));

		HorizontalLayout horizontalLayout1 = new HorizontalLayout();
		horizontalLayout1.addComponents(textAge, selectWorkClass,textFnlwgt,selectEduaction);

		HorizontalLayout horizontalLayout2 = new HorizontalLayout();
		horizontalLayout2.addComponents(selectMaritalStatus, selectOccupation,selectRelationship,selectRace,selectSex);

		HorizontalLayout horizontalLayout3 = new HorizontalLayout();
		horizontalLayout3.addComponents(textCapitalGain, textCapitalLoss,textHoursPerWeek,selectNativeCountry);

		horizontalLayout4 = new HorizontalLayout();
		lblResult = new Label();
		horizontalLayout4.addComponent(lblResult);
		horizontalLayout4.setVisible(false);

		horizontalLayout5 = new HorizontalLayout();
		horizontalLayout5.addComponents(bnYes,bnNo);
		horizontalLayout5.setVisible(false);

		this.addComponents(horizontalLayout1,horizontalLayout2,horizontalLayout3, bnIncome, horizontalLayout4, horizontalLayout5);

	}
	private void onClickYesButton(ClickEvent event) {
		sendResults(true);
	}

	private void onClickNoButton(ClickEvent event) {
		sendResults(false);
	}

	private void onClickIncomeButton(ClickEvent event) {

		try
		{

			ComboDto workclassSelected = selectWorkClass.getValue();
			ComboEducationDto educationSelected = selectEduaction.getValue();
			ComboDto occupationSelected = selectOccupation.getValue();
			ComboDto relationshipSelected = selectRelationship.getValue();
			ComboDto raceSelected = selectRace.getValue();
			ComboDto sexSelected = selectSex.getValue();
			ComboDto countrySelected = selectNativeCountry.getValue();
			ComboDto maritalStatusSelected = selectMaritalStatus.getValue();

			Double ageSelected = Double.valueOf(textAge.getValue());
			Double fnlwgtSelected = Double.valueOf(textFnlwgt.getValue());
			Double educationNumSelected = Double.valueOf(educationSelected.getNum());
			Double capitalGainSelected = Double.valueOf(textCapitalGain.getValue());
			Double capitalLossSelected = Double.valueOf(textCapitalLoss.getValue());
			Double hoursPerWeekSelected = Double.valueOf(textHoursPerWeek.getValue());

			OutputDto output = restclient.evaluateData(ageSelected, workclassSelected.getDesc(), fnlwgtSelected,
					educationSelected.getDesc(), educationNumSelected,	maritalStatusSelected.getDesc(),
					occupationSelected.getDesc(), relationshipSelected.getDesc(),
					raceSelected.getDesc(), sexSelected.getDesc(), capitalGainSelected,
							capitalLossSelected, hoursPerWeekSelected, countrySelected.getDesc());

			lblResult.setCaption("Your result is:" + output.getLabel());

			result = output.getLabel();

			horizontalLayout4.setVisible(true);
			horizontalLayout5.setVisible(true);
		} catch (Exception e) {
			log.error("Se ha producido una excepción al realizar la llamada al servicio:" + e.getMessage());

		}

	}

	private void sendResults(boolean evalresult) {

		ComboDto workclassSelected = selectWorkClass.getValue();
		ComboEducationDto educationSelected = selectEduaction.getValue();
		ComboDto occupationSelected = selectOccupation.getValue();
		ComboDto relationshipSelected = selectRelationship.getValue();
		ComboDto raceSelected = selectRace.getValue();
		ComboDto sexSelected = selectSex.getValue();
		ComboDto countrySelected = selectNativeCountry.getValue();
		ComboDto maritalStatusSelected = selectMaritalStatus.getValue();

		Double ageSelected = Double.valueOf(textAge.getValue());
		Double fnlwgtSelected = Double.valueOf(textFnlwgt.getValue());
		Double educationNumSelected = Double.valueOf(educationSelected.getNum());
		Double capitalGainSelected = Double.valueOf(textCapitalGain.getValue());
		Double capitalLossSelected = Double.valueOf(textCapitalLoss.getValue());
		Double hoursPerWeekSelected = Double.valueOf(textHoursPerWeek.getValue());



		InputResultDto input = InputResultDto.builder()
				.age(ageSelected)
				.workclass(workclassSelected.getDesc())
				.fnlwgt(fnlwgtSelected)
				.education(educationSelected.getDesc())
				.educationNum(educationNumSelected)
				.maritalStatus(maritalStatusSelected.getDesc())
				.occupation(occupationSelected.getDesc())
				.relationship(relationshipSelected.getDesc())
				.race(raceSelected.getDesc())
				.sex(sexSelected.getDesc())
				.capitalGain(capitalGainSelected)
				.capitalLoss(capitalLossSelected)
				.hoursPerWeek(hoursPerWeekSelected)
				.nativeCountry(countrySelected.getDesc())
				.label(result)
				.success(evalresult)
				.build();

		OutputResultDto outputDto = restclient.addResult(input);
		horizontalLayout5.setVisible(false);
	}

	private void initCombos () {

		//Combo Sex
		List<ComboDto> listSex = new ArrayList<>();
		listSex.add(new ComboDto(1, "Female"));
		listSex.add(new ComboDto(2, "Male"));


		selectSex =
		    new ComboBox<>("Sex:");
		selectSex.setItems(listSex);

		// Use the name property for item captions
		selectSex.setItemCaptionGenerator(ComboDto::getDesc);


		//Combo Race
		List<ComboDto> listRace = new ArrayList<>();
		listRace.add(new ComboDto(1, "White"));
		listRace.add(new ComboDto(2, "Asian-Pac-Islander"));
		listRace.add(new ComboDto(3, "Amer-Indian-Eskimo"));
		listRace.add(new ComboDto(4, "Black"));
		listRace.add(new ComboDto(5, "Other"));


		selectRace =
		    new ComboBox<>("Race:");
		selectRace.setItems(listRace);

		// Use the name property for item captions
		selectRace.setItemCaptionGenerator(ComboDto::getDesc);


		//Combo Relationship
		List<ComboDto> listRelationship = new ArrayList<>();
		listRelationship.add(new ComboDto(1, "Wife"));
		listRelationship.add(new ComboDto(2, "Own-child"));
		listRelationship.add(new ComboDto(3, "Husband"));
		listRelationship.add(new ComboDto(4, "Not-in-family"));
		listRelationship.add(new ComboDto(5, "Other-relative"));
		listRelationship.add(new ComboDto(6, "Unmarried"));

		selectRelationship =
		    new ComboBox<>("Relationship:");
		selectRelationship.setItems(listRelationship);

		// Use the name property for item captions
		selectRelationship.setItemCaptionGenerator(ComboDto::getDesc);

		//Combo Occupation
		List<ComboDto> listOccupation = new ArrayList<>();
		listOccupation.add(new ComboDto(1, "Tech-support"));
		listOccupation.add(new ComboDto(2, "Craft-repair"));
		listOccupation.add(new ComboDto(3, "Other-service"));
		listOccupation.add(new ComboDto(4, "Sales"));
		listOccupation.add(new ComboDto(5, "Exec-managerial"));
		listOccupation.add(new ComboDto(6, "Prof-specialty"));
		listOccupation.add(new ComboDto(7, "Handlers-cleaners"));
		listOccupation.add(new ComboDto(8, "Machine-op-inspct"));
		listOccupation.add(new ComboDto(9, "Adm-clerical"));
		listOccupation.add(new ComboDto(10, "Farming-fishing"));
		listOccupation.add(new ComboDto(11, "Transport-moving"));
		listOccupation.add(new ComboDto(12, "Priv-house-serv"));
		listOccupation.add(new ComboDto(13, "Protective-serv"));
		listOccupation.add(new ComboDto(14, "Armed-Forces"));

		selectOccupation =
		    new ComboBox<>("Occupation:");
		selectOccupation.setItems(listOccupation);

		// Use the name property for item captions
		selectOccupation.setItemCaptionGenerator(ComboDto::getDesc);


		//Combo Relationship
		List<ComboDto> listMaritalStatus = new ArrayList<>();
		listMaritalStatus.add(new ComboDto(1, "Married-civ-spouse"));
		listMaritalStatus.add(new ComboDto(2, "Divorced"));
		listMaritalStatus.add(new ComboDto(3, "Never-married"));
		listMaritalStatus.add(new ComboDto(4, "Separated"));
		listMaritalStatus.add(new ComboDto(5, "Widowed"));
		listMaritalStatus.add(new ComboDto(6, "Married-spouse-absent"));
		listMaritalStatus.add(new ComboDto(7, "Married-AF-spouse"));

		selectMaritalStatus =
		    new ComboBox<>("Marital Status:");
		selectMaritalStatus.setItems(listMaritalStatus);

		// Use the name property for item captions
		selectMaritalStatus.setItemCaptionGenerator(ComboDto::getDesc);

		//Combo workClass
		List<ComboDto> listWorkClass = new ArrayList<>();
		listWorkClass.add(new ComboDto(1, "Private"));
		listWorkClass.add(new ComboDto(2, "Self-emp-not-inc"));
		listWorkClass.add(new ComboDto(3, "Self-emp-inc"));
		listWorkClass.add(new ComboDto(4, "Federal-gov"));
		listWorkClass.add(new ComboDto(5, "Local-gov"));
		listWorkClass.add(new ComboDto(6, "State-gov"));
		listWorkClass.add(new ComboDto(7, "Without-pay"));
		listWorkClass.add(new ComboDto(8, "Never-worked"));

		selectWorkClass =
		    new ComboBox<>("WorkClass:");
		selectWorkClass.setItems(listWorkClass);

		// Use the name property for item captions
		selectWorkClass.setItemCaptionGenerator(ComboDto::getDesc);

		//Combo Education
		List<ComboEducationDto> listEducation = new ArrayList<>();
		listEducation.add(new ComboEducationDto(1, "Bachelors",13));
		listEducation.add(new ComboEducationDto(2, "Some-college",10));
		listEducation.add(new ComboEducationDto(3, "11th",7));
		listEducation.add(new ComboEducationDto(4, "HS-grad",9));
		listEducation.add(new ComboEducationDto(5, "Prof-school",15));
		listEducation.add(new ComboEducationDto(6, "Assoc-acdm",12));
		listEducation.add(new ComboEducationDto(7, "Assoc-voc",11));
		listEducation.add(new ComboEducationDto(8, "9th",5));
		listEducation.add(new ComboEducationDto(9, "7th-8th",4));
		listEducation.add(new ComboEducationDto(10, "12th",8));
		listEducation.add(new ComboEducationDto(11, "Masters",14));
		listEducation.add(new ComboEducationDto(12, "1st-4th",2));
		listEducation.add(new ComboEducationDto(13, "10th",6));
		listEducation.add(new ComboEducationDto(14, "Doctorate",16));
		listEducation.add(new ComboEducationDto(15, "5th-6th",3));
		listEducation.add(new ComboEducationDto(16, "Preschool",1));


		selectEduaction =
		    new ComboBox<>("Education:");
		selectEduaction.setItems(listEducation);

		// Use the name property for item captions
		selectEduaction.setItemCaptionGenerator(ComboEducationDto::getDesc);


		//Combo Native Country
		List<ComboDto> listCountry = new ArrayList<>();
		listCountry.add(new ComboDto(1, "United-States"));
		listCountry.add(new ComboDto(2, "Cambodia"));
		listCountry.add(new ComboDto(3, "England"));
		listCountry.add(new ComboDto(4, "Puerto-Rico"));
		listCountry.add(new ComboDto(5, "Canada"));
		listCountry.add(new ComboDto(6, "Germany"));
		listCountry.add(new ComboDto(7, "Outlying-US(Guam-USVI-etc)"));
		listCountry.add(new ComboDto(8, "India"));
		listCountry.add(new ComboDto(9, "Japan"));
		listCountry.add(new ComboDto(10, "Greece"));
		listCountry.add(new ComboDto(11, "South"));
		listCountry.add(new ComboDto(12, "China"));
		listCountry.add(new ComboDto(13, "Cuba"));
		listCountry.add(new ComboDto(14, "Iran"));
		listCountry.add(new ComboDto(15, "Honduras"));
		listCountry.add(new ComboDto(16, "Philippines"));
		listCountry.add(new ComboDto(17, "Italy"));
		listCountry.add(new ComboDto(18, "Poland"));
		listCountry.add(new ComboDto(19, "Jamaica"));
		listCountry.add(new ComboDto(20, "Vietnam"));
		listCountry.add(new ComboDto(21, "Mexico"));
		listCountry.add(new ComboDto(22, "Portugal"));
		listCountry.add(new ComboDto(23, "Ireland"));
		listCountry.add(new ComboDto(24, "France"));
		listCountry.add(new ComboDto(25, "Dominican-Republic"));
		listCountry.add(new ComboDto(26, "Laos"));
		listCountry.add(new ComboDto(27, "Ecuador"));
		listCountry.add(new ComboDto(28, "Taiwan"));
		listCountry.add(new ComboDto(29, "Haiti"));
		listCountry.add(new ComboDto(30, "Columbia"));
		listCountry.add(new ComboDto(31, "Hungary"));
		listCountry.add(new ComboDto(32, "Guatemala"));
		listCountry.add(new ComboDto(33, "Nicaragua"));
		listCountry.add(new ComboDto(34, "Scotland"));
		listCountry.add(new ComboDto(35, "Thailand"));
		listCountry.add(new ComboDto(36, "Yugoslavia"));
		listCountry.add(new ComboDto(37, "El-Salvador"));
		listCountry.add(new ComboDto(38, "Trinadad&Tobago"));
		listCountry.add(new ComboDto(39, "Peru"));
		listCountry.add(new ComboDto(40, "Hong"));
		listCountry.add(new ComboDto(41, "Holand-Netherlands"));


		selectNativeCountry =
		    new ComboBox<>("Native Country:");
		selectNativeCountry.setItems(listCountry);

		// Use the name property for item captions
		selectNativeCountry.setItemCaptionGenerator(ComboDto::getDesc);
	}


}
