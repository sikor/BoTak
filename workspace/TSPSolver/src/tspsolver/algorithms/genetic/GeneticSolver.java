package tspsolver.algorithms.genetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import tspsolver.algorithms.ISolver;
import tspsolver.algorithms.IterationResult;

public class GeneticSolver implements ISolver{
	
	
	int maxIterationCount = 100;
	int iterationNumber = 0;
	
	double odleglosci[][] = {{0, 4, 2, 4, 4},
			{4, 0, 4, 6, 2},
			{2, 4, 0, 2, 2},
			{4, 6, 2, 0, 4},
			{4, 2, 2, 4, 0}};
	int N = 5, K = 2, M = 1, m = 1;

	public GeneticSolver(double[][] distances, Properties properties) {
		//Hardcoded for now and... in polish, booya!
		//...
	}

	Random r = new Random();
	
	int losuj(int n) {
		return r.nextInt(n);
	}

	class Osobnik {
		Chromosomy dna;
		double odleglosc;
	}
	
	class Chromosomy {
		ArrayList<Integer> t;
		
		Chromosomy() {
			t = new ArrayList<Integer>();
		}
		
		Chromosomy(Chromosomy and) {
			t = (ArrayList<Integer>)and.t.clone();
		}
		
		void wypelnij() {
			int los;
			t.add(new Integer(0));
			for(int i = 1; i < N; ++i) {
				do {
					los = losuj(N - 1) + 1;
				} while(t.contains(new Integer(los)));
				t.add(new Integer(los));
			}
		}
		
		void krzyzujZ(Chromosomy and) {
			int odKtorego = losuj(N - 2) + 1;
			int ile = losuj(N - odKtorego);
			
			List<Integer> pod = and.t.subList(odKtorego, odKtorego + ile);
			t.removeAll(pod);
			t.addAll(odKtorego, pod);
		}
		
		void mutuj() {
			for(int i = 0; i < m; ++i) {
				zamienLosowe();
			}
		}
		
		void zamienLosowe() {
			int ten = losuj(N - 1) + 1, zTym = losuj(N - 1) + 1;
			Integer i = t.get(ten);
			t.set(ten, t.get(zTym));
			t.set(zTym, i);
		}
		
		double odleglosc() {
			double r = 0;
			for(int i = 0; i < N -1; ++i) {
				r += odleglosci[t.get(i)][t.get(i + 1)];
			}
			return r;
		}

		int [] doTablicyIntow() {
			int r[] = new int[N], i = 0;
			for(Integer m : t) {
				r[i] = m;
				++i;
			}
			return r;
		}
	}
	
	Osobnik obecni[] = null;
	
	Osobnik skopiujOsobnika(Osobnik osobnik) {
		Osobnik kopia = new Osobnik();
		kopia.odleglosc = osobnik.odleglosc;
		kopia.dna = new Chromosomy(osobnik.dna);
		return kopia;
	}
	
	void krzyzuj() {
		int ten = losuj(N), zTym;
		do {
			zTym = losuj(N);
		} while(zTym == ten);
		Osobnik wTen = skopiujOsobnika(obecni[ten]),
				lubTen = skopiujOsobnika(obecni[zTym]);
		wTen.dna.krzyzujZ(obecni[zTym].dna);
		wTen.odleglosc = wTen.dna.odleglosc();
		lubTen.dna.krzyzujZ(obecni[ten].dna);
		lubTen.odleglosc = lubTen.dna.odleglosc();
		Osobnik wybrani[] = {obecni[ten], obecni[zTym], wTen, lubTen};
		double suma = 0;
		for(Osobnik o : wybrani) {
			suma += o.odleglosc;
		}
		int pierwszy = wybierzSkrzyzowanego(wybrani, suma), drugi;
		do {
			drugi = wybierzSkrzyzowanego(wybrani, suma);
		} while(drugi == pierwszy);
		obecni[ten] = wybrani[pierwszy];
		obecni[zTym] = wybrani[drugi];
	}
	
	int wybierzSkrzyzowanego(Osobnik wybrani[], double suma) {
		int w = 0;
		double los = losuj((int)suma - 1);
		while(los > wybrani[w].odleglosc) {
			los -= wybrani[w].odleglosc;
			++w;
		}
		return w;
	}
	
	void krzyzowanie() {
		for(int i = 0; i < K; ++i) {
			krzyzuj();
		}
	}
	
	void mutacja() {
		for(int i = 0; i < M; ++i) {
			int l = losuj(N);
			obecni[l].dna.mutuj();
			obecni[l].odleglosc = obecni[l].dna.odleglosc();
		}
	}
	
	public IterationResult nextIteration() {
		if(iterationNumber == maxIterationCount){
			return null;
		}
		if(obecni == null) {
			pierwszaPopulacja();
		} else {
			krzyzowanie();
			mutacja();
		}
		
		Osobnik naj = najlepszy();
		IterationResult r = new IterationResult(naj.dna.doTablicyIntow(),naj.odleglosc, iterationNumber++);
		return r;
	}
	
	Osobnik najlepszy() {
		Osobnik naj = obecni[0];
		for(Osobnik c : obecni) {
			System.out.println(c.odleglosc);
			if(c.odleglosc < naj.odleglosc) {
				naj = c;
			}
		}
		return naj;
	}
	
	void pierwszaPopulacja() {
		obecni = new Osobnik[N];
		for(int i = 0; i < N; ++i) {
			obecni[i] = new Osobnik();
			wypelnijLosowo(obecni[i]);
		}
	}
	
	void wypelnijLosowo(Osobnik c) {
		c.dna = new Chromosomy();
		c.dna.wypelnij();
		c.odleglosc = c.dna.odleglosc();
	}


}
