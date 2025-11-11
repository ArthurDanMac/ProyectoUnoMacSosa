import dotenv from "dotenv";
import express from "express";
import taskRoutes from "./routes/tasks.js";
import authRoutes from "./routes/auth.js";

dotenv.config();
const app = express();

app.use(express.json());
app.use("/api/tasks", taskRoutes);
app.use("/api/auth", authRoutes);

app.get("/", (req, res) => res.send("API Tasks funcionando ✅"));

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => console.log(`Servidor corriendo en puerto ${PORT}`));