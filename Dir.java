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

	public Dir turnRight() {
		Dir dir = this;
		if (this == Up) {
			dir = Dir.Right;
		} else if (this == Right) {
			dir = Dir.Down;
		} else if (this == Down) {
			dir = Dir.Left;
		} else if (this == Left) {
			dir = Dir.Up;
		}
		return dir;
	}

	public Dir turnLeft() {
		Dir dir = this;
		if (this == Up) {
			dir = Dir.Left;
		} else if (this == Left) {
			dir = Dir.Down;
		} else if (this == Down) {
			dir = Dir.Right;
		} else if (this == Right) {
			dir = Dir.Up;
		}
		return dir;
	}

}