
public class testClient {
	public static void main(String[] args) {
		TextAssociator t = new TextAssociator();
		t.addNewWord("x");
		t.addNewWord("x");
		t.addAssociation("x", "letter");
		t.addAssociation("x", "y");
		t.addAssociation("x", "y");
		t.prettyPrint();
	}
}
