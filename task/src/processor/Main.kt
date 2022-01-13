package processor

import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.pow

class Matrix(_rows: Int = 0,
             _columns: Int = 0,
             _error: Boolean = false ,
             _loadMatrix: MutableList<MutableList<Double>> = MutableList(0) {MutableList(0) {0.0}}) {

    val rows = _rows
    val columns = _columns
    val matrix = _loadMatrix
    val error = _error

        operator fun plus(other: Matrix): Matrix { // + OPERATOR OVERLOAD
        if (rows == other.rows && columns == other.columns) {
            var solved = MutableList(rows) {MutableList(columns) {0.0}}
            for(i in 0 until rows) {
                for (j in 0 until columns){
                    solved[i][j]= matrix[i][j] + other.matrix[i][j]
                }
            }
            return Matrix(rows, columns, _loadMatrix = solved)
        }else {
            return Matrix(_error = true)
        }
    }

    operator fun times(other: Int): Matrix { // * OPERATOR OVERLOAD ESCALAR MULTIPLICATION
        var solved = MutableList(rows) {MutableList(columns) {0.0}}
        for(i in 0 until rows) {
            for (j in 0 until columns){
                solved[i][j]= matrix[i][j] * other
            }
        }
        return Matrix(rows, columns, _loadMatrix = solved)
    }

    operator fun times(other: Double): Matrix { // * OPERATOR OVERLOAD ESCALAR MULTIPLICATION
        var solved = MutableList(rows) {MutableList(columns) {0.0}}
        for(i in 0 until rows) {
            for (j in 0 until columns){
                solved[i][j]= matrix[i][j] * other
            }
        }
        return Matrix(rows, columns, _loadMatrix = solved)
    }

    operator fun times(other: Matrix): Matrix { // * OPERATOR OVERLOAD MATRIX MULTIPLICATION
        var solved = MutableList(rows) {MutableList(other.columns) {0.0}}
        print(matrix)
        print(other.matrix)
        if (columns == other.rows) {
            for (i in 0 until rows) {
                for (j in 0 until other.columns) {
                    for (k in 0 until columns) {
                        solved[i][j] += matrix[i][k] * other.matrix[k][j]
                    }
                }
            }
            return Matrix(rows, other.columns, _loadMatrix = solved)
        } else{
            return Matrix(_error = true)
        }
    }
}

fun print(a: Matrix) { // PRINT FUNCTION OVERLOAD
    if (a.error) {
        print("The operation cannot be performed.")
    } else {
        for(i in 0 until a.rows) {
            for (j in 0 until a.columns){
                print("${a.matrix[i][j]} ")
            }
            if (i < a.rows) println()
        }
    }
}

fun transpose (a: Matrix, option: Int): Matrix {
    when(option) {
        1 -> {
            val transpose = MutableList(a.columns) {MutableList(a.rows) {0.0}}
            for (i in 0 until a.rows) {
                for (j in 0 until a.columns) {
                    transpose[j][i] = a.matrix[i][j]
                }
            }
            return Matrix(a.columns, a.rows, _loadMatrix = transpose)
        }
        2 -> {
            val transpose = MutableList(a.columns) {MutableList(a.rows) {0.0}}
            for (i in 0 until a.rows) {
                for (j in 0 until a.columns) {
                    transpose[a.columns -1 - j][a.rows -1 - i] = a.matrix[i][j]
                }
            }
            return Matrix(a.columns, a.rows, _loadMatrix = transpose)
        }
        3 -> {
            val transpose = MutableList(a.columns) {MutableList(a.rows) {0.0}}
            for (i in 0 until a.columns) {
                for (j in 0 until a.rows) {
                    transpose[i][a. rows -1 -j] = a.matrix[i][j]
                }
            }
            return Matrix(a.columns, a.rows, _loadMatrix = transpose)
        }
        4 -> {
            val transpose = MutableList(a.columns) {MutableList(a.rows) {0.0}}
            for (i in 0 until a.columns) {
                for (j in 0 until a.rows) {
                    transpose[a.columns -1 - i][j] = a.matrix[i][j]
                }
            }
            return Matrix(a.columns, a.rows, _loadMatrix = transpose)
        }
        else -> Matrix()
    }
    return Matrix()
}

class Matrix2(_rows: Int, _cols: Int ) {
    val rows = _rows
    val cols = _cols
    val array: MutableList<MutableList<BigDecimal>> = mutableListOf()
    init {
        for (i in 0 until rows) {
            this.array.add(MutableList(cols){ 0.toBigDecimal() })
        }
    }
    fun fill() {
        for (i in 0 until rows) {
            val rowArray = readLine()!!.split(" ")
            for (j in 0 until cols) {
                array[i][j] = rowArray[j].toBigDecimal()
            }
        }
    }
    operator fun plus(matrix: Matrix2): Matrix2 {
        if (this.cols == matrix.cols && this.rows == matrix.rows) {
            val result = Matrix2(this.rows, this.cols)
            for (i in 0 until rows) {
                for (j in 0 until cols) {
                    result.array[i][j] = this.array[i][j] + matrix.array[i][j]
                }
            }
            return result
        } else {
            throw Exception("IncorrectSize")
        }
    }

    operator fun times(matrix: Matrix2): Matrix2 {
        if (this.cols == matrix.rows) {
            val result = Matrix2(this.rows, matrix.cols)
            for (i in 0 until result.rows) {
                for (j in 0 until result.cols) {
                    result.array[i][j] = this.array[i].foldIndexed(0.toBigDecimal()) { index, acc, v -> acc + v * matrix.array[index][j] }
                }
            }
            return result
        } else {
            throw Exception("IncorrectSize")
        }
    }

    operator fun times(mult: BigDecimal): Matrix2 {
        val result = Matrix2(this.rows, this.cols)
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                result.array[i][j] = this.array[i][j] * mult
            }
        }
        return result
    }

    fun mainDiagonalTransposition(): Matrix2 {
        if (this.rows == this.cols) {
            val result = Matrix2(this.rows, this.cols)
            for (i in 0 until rows) {
                for (j in 0 until cols) {
                    result.array[i][j] = this.array[j][i]
                }
            }
            return result
        } else {
            throw Exception("IncorrectSize")
        }
    }

    fun sideDiagonalTransposition(): Matrix2 {
        if (this.rows == this.cols) {
            val result = Matrix2(this.rows, this.cols)
            for (i in 0 until rows) {
                for (j in 0 until cols) {
                    result.array[i][j] = this.array[this.cols - j - 1][this.rows - i - 1]
                }
            }
            return result
        } else {
            throw Exception("IncorrectSize")
        }
    }

    fun verticalLineTransposition(): Matrix2 {
        val result = Matrix2(this.rows, this.cols)
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                result.array[i][j] = this.array[i][this.cols - j - 1]
            }
        }
        return result
    }

    fun horizontalLineTransposition(): Matrix2 {
        val result = Matrix2(this.rows, this.cols)
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                result.array[i][j] = this.array[this.rows - i - 1][j]
            }
        }
        return result
    }

    fun getMinor(row: Int, col: Int): Matrix2 {
        if (row < rows && col < cols){
            val result = Matrix2(this.rows - 1, this.cols - 1)
            for (i in 0 until rows) {
                for (j in 0 until cols) {
                    if (i != row && j != col) {
                        val newRow = if (i > row) i - 1 else i
                        val newCol = if (j > col) j - 1 else j
                        result.array[newRow][newCol] = this.array[i][j]
                    }
                }
            }
            return result
        } else {
            throw Exception("IncorrectIndex")
        }
    }

    fun inverse(): Matrix2 {
        if (rows == cols) {
            val det = calcDeterminant(this)
            if (det != 0.toBigDecimal()) {
                val minm = getMinorMatrix()
                val tr = minm.mainDiagonalTransposition()
                val k: BigDecimal = 1.toBigDecimal().divide(det, 20, RoundingMode.HALF_UP)
                return tr * k
            } else {
                throw Exception("det - 0")
            }
        } else {
            throw Exception("IncorrectIndex")
        }
    }

    private fun getMinorMatrix(): Matrix2 {
        val result = Matrix2(this.rows, this.cols)
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                result.array[i][j] = calcDeterminant(getMinor(i,j)) * (((-1).toDouble()).pow((i + j).toDouble())).toBigDecimal()
            }
        }
        return result
    }


    fun print() {
        this.array.forEach { i -> println(i.joinToString(" ")) }
    }

    companion object {
        fun calcDeterminant(matrix: Matrix2): BigDecimal {
            return if (matrix.rows == 2 && matrix.cols == 2) {
                matrix.array[0][0] * matrix.array[1][1] - matrix.array[0][1] * matrix.array[1][0]
            } else if (matrix.rows == 1 && matrix.cols == 1) {
                matrix.array[0][0]
            } else {
                var result = 0.toBigDecimal()
                val i = 0
                for (j in 0 until matrix.cols) {
                    result += matrix.array[i][j] * (((-1).toDouble()).pow((i + j).toDouble())).toBigDecimal() * calcDeterminant(matrix.getMinor(i,j))
                }
                result
            }
        }
    }
}

fun getInverseMatrix() {
    println("Enter matrix size:")
    val size1 = readLine()!!.split(" ")
    val matrix1 = Matrix2(size1[0].toInt(), size1[1].toInt())
    println("Enter matrix:")
    matrix1.fill()
    println("The result is:")
    val matrix3 = matrix1.inverse()
    matrix3.print()
}

fun getDeterminant() {
    println("Enter matrix size:")
    val size1 = readLine()!!.split(" ")
    val matrix1 = Matrix2(size1[0].toInt(), size1[1].toInt())
    println("Enter matrix:")
    matrix1.fill()
    println("The result is:")
    println(Matrix2.calcDeterminant(matrix1))
}


fun readMatrix(number: Int = 0): Matrix { //MATRIX INPUT READER
    if (number == 0) print("Enter size of matrix: ")
    if (number == 1) print("Enter size of first matrix: ")
    if (number == 2) print("Enter size of second matrix: ")
    val (rows,columns) = readLine()!!.split(" ").map { it.toInt() }
    val initialMatrix = MutableList(rows) {MutableList(columns) {0.0}}
    if (number == 0) println("Enter matrix:")
    if (number == 1) println("Enter first matrix:")
    if (number == 2) println("Enter second matrix:")
    for (i in 0 until rows) {
        print("> ")
        var load = readLine()!!.split(" ").map { it.toDouble() }
        for (j in 0 until columns) {
            initialMatrix[i][j] = load[j]
        }
    }
    return Matrix(rows, columns, _loadMatrix = initialMatrix)
}

fun menu(): Boolean{
    println("1. Add matrices")
    println("2. Multiply matrix by a constant")
    println("3. Multiply matrices")
    println("4. Transpose matrices")
    println("5. Calculate a determinant")
    println("6. Inverse matrix")
    println("0. Exit")
    print("Your choice: ")
    var choice = readLine()!!.toInt()
    when (choice) {
        0 -> return true
        1 -> {
            val matrixOne = readMatrix(1)
            val matrixTwo = readMatrix(2)
            val result = matrixOne + matrixTwo
            println("The result is:")
            print(result)
        }
        2 -> {
            val matrixOne = readMatrix(1)
            println("Enter constant: > ")
            val constant = readLine()!!.toDouble()
            val result = matrixOne * constant
            println("The result is:")
            print(result)
        }
        3 -> {
            val matrixOne = readMatrix(1)
            val matrixTwo = readMatrix(2)
            val result = matrixOne * matrixTwo
            println("The result is:")
            print(result)
        }
        4 -> {
            println("1. Main diagonal")
            println("2. Side diagonal")
            println("3. Vertical line")
            println("4. Horizontal line")
            print("Your choice: ")
            val option = readLine()!!.toInt()
            val matrixOne = readMatrix()
            val result = transpose(matrixOne, option)
            println("The result is:")
            print(result)
        }
        5 -> {
            getDeterminant()
        }
        6 -> {
            getInverseMatrix()
        }
        else -> return false
    }
    return false
}

fun main() {
    var exit :Boolean = false
    while(!exit) {
        exit = menu()
    }
}