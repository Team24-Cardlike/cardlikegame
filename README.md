<h1>Rouges of Midgard</h1>

<h2>Requirements:</h2>
<ul>
    <li>Java 21+</li>
    <li>Maven 3.8+</li>
    <li>LibGDX 1.12.1+</li>
    <li>Gson 2.10.1+</li>
    <li>JUnit 4.13.2+</li>
</ul>

<h2>Running the game:</h2>

<h3>Option 1:</h3>
<p>Run the following command when in the directory outside the src folder (cardlikegame):</p>
<pre><code>src/main/java/org/example/DesktopLauncher.java</code></pre>

<h3>Option 2:</h3>
<p>Run the following command (requires Maven):</p>
<pre><code>mvn compile exec:java</code></pre>

<h3>Option 3:</h3>
<p>Run it through an IDE.</p>

<h2>Tutorial:</h2>
<p>The game is quite simple. After launching, press play (or load if u have saved before). Then you click on the leftmost node in the map. You will be taken to the first fight where you will use your card to defeat your opponent.</p> <p>Information about gameplay can be found in the handbook by pressing it during the fight. After your first fight, you will be taken back to the map where you now can press "Anton's Shop" to buy upgrades for your upcoming fights you are allowed max four upgrades, so choose wisely.</p> <p>Have fun! </p>

<h2>Known issues:</h2>
<ul>
    <li>If you get errors about assets not loading, try running <code>mvn clean</code> in the terminal.</li>
    <li>If you buy too many upgrades the game will ultimately crash and you will lose your progress if you didn't save before.</li>
    <li>Your avatar isn't visible on the map until after your first fight.</li>
</ul>
