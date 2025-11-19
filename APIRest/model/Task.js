import pool from "../config/db.js";

export class Task {
  static async getAll() {
    const [rows] = await pool.query("SELECT * FROM task");
    return rows;
  }

  static async getById(id) {
    const [rows] = await pool.query("SELECT * FROM task WHERE id = ?", [id]);
    return rows[0];
  }

  static async create(task) {
    const { name,plannedD, status } = task;
    const [result] = await pool.query(
      "INSERT INTO task (name, plannedD, status) VALUES (?, ?, ?)",
      [name,plannedD, status ]
    );
    return { id: result.insertId, ...task };
  }

  static async update(id, task) {
    const {  name,plannedD, status } = task;
    await pool.query(
      "UPDATE task SET name=?, plannedD=? WHERE id=?",
      [name, plannedD, id]
    );
  }

  static async delete(id) {
    await pool.query("DELETE FROM task WHERE id=?", [id]);
  }
}
