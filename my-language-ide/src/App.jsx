import { useState } from "react";
import "./App.css";

function App() {
  const [code, setCode] = useState("");
  const [output, setOutput] = useState("");

  const runCode = async () => {
    try {
      const response = await fetch("http://localhost:8000/execute", {
        method: "POST",
        body: code,
      });
      const result = await response.text();
      setOutput(result);
    } catch (err) {
      setOutput("Error: Could not connect to Java engine. Is it running?");
    }
  };

  return (
    <div style={{ padding: "20px" }}>
      <h1>MyLanguage Workbench</h1>
      <textarea
        style={{ width: "100%", height: "200px" }}
        value={code}
        onChange={(e) => setCode(e.target.value)}
        placeholder="Type your code here..."
      />
      <br />
      <button
        onClick={runCode}
        style={{ padding: "10px 20px", fontSize: "16px" }}
      >
        Run Code
      </button>
      <h3>Output:</h3>
      <pre style={{ background: "#eee", padding: "10px" }}>{output}</pre>
    </div>
  );
}

export default App;
