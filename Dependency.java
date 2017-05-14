public class Dependency{
	String dependencyType; //WAW,WAR,RAW
	String register; //r1,r2
	int addressDependentOn;
	int addressOfDependent;

	public Dependency(int address, int addressOfDependent, String register, String dependencyType){
		this.addressDependentOn = address;
		this.addressOfDependent = addressOfDependent;
		this.register = register;
		this.dependencyType = dependencyType;
	}
}