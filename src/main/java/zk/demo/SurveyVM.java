package zk.demo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelArray;
import org.zkoss.zul.SimpleListModel;

public class SurveyVM {
	private static final List<String> COUNTRY_LIST = new ArrayList<>();

	private String name;
	private SimpleListModel<String> country = new SimpleListModel<>(COUNTRY_LIST);
	private ListModelArray<String> proglangs;

	static {
		loadCountry();
	}

	private static void loadCountry() {
		try (InputStream is = SurveyVM.class.getClassLoader().getResourceAsStream("country.txt");
		     InputStreamReader isr = new InputStreamReader(is);
		     BufferedReader br = new BufferedReader(isr)) {
			while (true) {
				String line = br.readLine();
				if (line == null) break;
				COUNTRY_LIST.add(line);
			}
		} catch (Exception ignored) {
		}
	}

    @Init
	public void init() {
		proglangs = new ListModelArray<>(new String[] {
			"Java", "C", "Python", "C++", "C#", "Visual Basic .NET",
			"JavaScript", "SQL", "PHP", "Objective-C", "Groovy", 
			"Assembly language", "Delphi/Object Pascal", "Go", "Ruby",
			"Swift", "Visual Basic", "MATLAB", "R", "Perl", "SAS", "D",
			"PL/SQL", "Dart", "F#", "Transact-SQL", "ABSP", "Scratch",
			"TypeScript", "Scala", "COBOL", "Lisp", "Rust", "Fortran",
			"Ada", "Julia", "Kotlin", "ActionScript", "RPG", "Logo",
			"Lua", "Prolog", "Scheme", "PostScript", "LabVIEW",
			"VBScript", "Bash", "PL/I", "MS-DOS batch", "Haskell"
		});
		proglangs.setMultiple(true);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ListModel<String> getCountry() {
		return country;
	}

	public ListModel<String> getProglangs() {
		return proglangs;
	}

	@Command
	public void show() {
		Clients.showNotification(String.format(
			"Thanks %s.<br>You live in %s.<br>You are familiar with %s.",
			name, country.getSelection().stream().findFirst().orElse("an unknown place"),
			proglangs.getSelection().stream().collect(Collectors.joining(", "))
		));
	}
}