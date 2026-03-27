# PieChart
Hello Android Devloper 
<img width="250" height="600" alt="Screenshot 2569-03-26 at 12 24 29" src="https://github.com/user-attachments/assets/e9968663-c9ff-45b0-b625-01c276acc5b9" />

How to get Library

Step 1. Add the JitPack repository to your build file

	dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url = uri("https://jitpack.io") }
		}
	}
 
Step 2. Add the dependency
 
	dependencies {
	        implementation("com.github.TonRobotic:PieChart:1.0.1")
	}

Step 3. Add to XML
    ** borderPieSize depend on layout_width , layout_height

	<com.mt.piechartm.PieChart
		android:id="@+id/pieChart"
		android:layout_width="120dp"
		android:layout_height="120dp"
		app:scaleColor="@color/gray_400"
		app:borderPieSize="36dp"
		app:layout_constraintBottom_toBottomOf="parent"
		app:layout_constraintEnd_toEndOf="parent"
		app:layout_constraintStart_toStartOf="parent"
		app:layout_constraintTop_toTopOf="parent" />
   
Step 4. Add to Activity or fragment Class

	class MainActivity : AppCompatActivity() {
	
	    private lateinit var pieChart: PieChart
	
	    override fun onCreate(savedInstanceState: Bundle?) {
	        super.onCreate(savedInstanceState)
	        enableEdgeToEdge()
	        setContentView(R.layout.activity_main)
	        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
	            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
	            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
	            insets
	        }
	
	        // define pie chart //
	        pieChart = findViewById(R.id.pieChart)
	
	        // mock pie model //
	        val pieDataList = listOf(
	            PieData(
	                title = "Python",
	                value = 10000f,
	                color = getColor(android.R.color.holo_blue_dark)
	            ),
	            PieData(
	                title = "Java",
	                value = 2000f,
	                color = getColor(android.R.color.holo_green_dark)
	            ),
	            PieData(
	                title = "Kotlin",
	                value = 5000f,
	                color = getColor(android.R.color.darker_gray)
	            ),
	            PieData(
	                title = "JavaScript",
	                value = 8500f,
	                color = getColor(android.R.color.holo_red_dark)
	            ),
	            PieData(
	                title = "PHP",
	                value = 4000f,
	                color = getColor(android.R.color.holo_green_light)
	            ),
	        )
	
	        // set pie chart //
	        pieChart.setPieValue(
	            pieDataList,
	            isAnimate = true,
	            delay = 1000,
	            isRevert = true,
	            startPoint = 135f
	        )
	    }
	}
