package oscuroweb.ia.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Deprecated
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncomeResultDto implements Serializable {
	
	private static final long serialVersionUID = 2086755262127261284L;
	
	/** _c0 - Age: continuous*/
	private Double age;
	/** _c1 - Workclass: Private, Self-emp-not-inc, Self-emp-inc, Federal-gov, Local-gov, State-gov, Without-pay, Never-worked */
	private String workclass;
	/** _c2 - Sampling weight: continuous */
	private Double fnlwgt;
	/** _c3 - education: Bachelors, Some-college, 11th, HS-grad, Prof-school, Assoc-acdm, Assoc-voc, 9th, 7th-8th, 12th, Masters, 1st-4th, 10th, Doctorate, 5th-6th, Preschool */
	private String education;
	/** _c4 - education-num: continuous */
	private Double educationNum;
	/** _c5 - marital-status: Married-civ-spouse, Divorced, Never-married, Separated, Widowed, Married-spouse-absent, Married-AF-spouse. */
	private String maritalStatus;
	/** _c6 - occupation: Tech-support, Craft-repair, Other-service, Sales, Exec-managerial, Prof-specialty, Handlers-cleaners, Machine-op-inspct, Adm-clerical, Farming-fishing, Transport-moving, Priv-house-serv, Protective-serv, Armed-Forces.*/
	private String occupation;
	/** _c7 - relationship: Wife, Own-child, Husband, Not-in-family, Other-relative, Unmarried. */
	private String relationship;
	/** _c8 - race: White, Asian-Pac-Islander, Amer-Indian-Eskimo, Other, Black. */
	private String race;
	/** _c9 - sex: Female, Male. */
	private String sex;
	/** _c10 - capital-gain: continuous. */
	private Double capitalGain;
	/** _c11 - capital-loss: continuous. */
	private Double capitalLoss;
	/** _c12 - hours-per-week: continuous. */
	private Double hoursPerWeek;
	/** _c13 - native-country: United-States, Cambodia, England, Puerto-Rico, Canada, Germany, Outlying-US(Guam-USVI-etc), India, Japan, Greece, South, China, Cuba, Iran, Honduras, Philippines, Italy, Poland, Jamaica, Vietnam, Mexico, Portugal, Ireland, France, Dominican-Republic, Laos, Ecuador, Taiwan, Haiti, Columbia, Hungary, Guatemala, Nicaragua, Scotland, Thailand, Yugoslavia, El-Salvador, Trinadad&Tobago, Peru, Hong, Holand-Netherlands.*/
	private String nativeCountry;
	/** label: >50K, <=50K. */
	private String label;
	/** TRUE if prediction is correct.*/
	private Boolean success;
}
