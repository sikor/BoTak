package tspsolver.model;

import tspsolver.annotations.Immutable;


@Immutable
public class GeneticParameters extends AlgorithmParameters{

	private final int liczbaOsobnikow;
	private final int  liczbaKrzyzowan;
	private final int liczbaMutacji;
	private final int liczbaPodmianWObrebieMutacji;

	public GeneticParameters(int iterationCount, int liczbaOsobnikow, int liczbaKrzyzowan, int liczbaMutacji,
			int liczbaPodmianWObrebieMutacji){
		super(iterationCount);
		this.liczbaOsobnikow = liczbaOsobnikow;
		this.liczbaKrzyzowan = liczbaKrzyzowan;
		this.liczbaMutacji = liczbaMutacji;
		this.liczbaPodmianWObrebieMutacji = liczbaPodmianWObrebieMutacji;
	}

	public int getLiczbaOsobnikow() {
		return liczbaOsobnikow;
	}


	public int getLiczbaKrzyzowan() {
		return liczbaKrzyzowan;
	}

	public int getLiczbaMutacji() {
		return liczbaMutacji;

	}

	public int getLiczbaPodmianWObrebieMutacji() {
		return liczbaPodmianWObrebieMutacji;
	}




}
