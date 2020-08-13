public enum Dir {
	Up(0),
	Down(180),
	Left(270),
	Right(90);
	
	private int angle;

	Dir() {
		this.angle = 0;
	}

	Dir(int angle) {
		this.angle = angle;
	}

	public int getAngle() {
		return this.angle;
	}
}