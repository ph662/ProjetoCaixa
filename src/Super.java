interface A {
	public void aMethod();
}

interface B {
	public void bMethod();
}

interface C extends A, B {
	public void cMethod();
}

class D implements B {
	public void bMethod() {
		System.out.println("0");
	}
}

class E extends D implements C {

	@Override
	public void aMethod() {
	}

	@Override
	public void bMethod() {
		System.out.println("1");
	}

	@Override
	public void cMethod() {
	}

	public static void main(String[] args) {

		D e = (D) (new E());
		e.bMethod();

	}
}