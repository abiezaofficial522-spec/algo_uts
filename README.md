## Penjelasan Kode SortingDemo.java

### 1. Bubble Sort Basic

Cara kerjanya sederhana: bandingkan dua elemen berdampingan, lalu tukar posisi jika elemen kiri lebih besar dari kanan. Proses ini diulang sebanyak `n-1` pass, sehingga elemen terbesar selalu "menggelembung" ke posisi paling akhir di setiap putaran.

**Contoh step-by-step** dengan `[5, 3, 8, 4, 2]`:

```
Pass 1: [5,3] -> swap -> [3,5,8,4,2]
        [5,8] -> ok   -> [3,5,8,4,2]
        [8,4] -> swap -> [3,5,4,8,2]
        [8,2] -> swap -> [3,5,4,2,8]  ← 8 sudah di posisi benar

Pass 2: [3,5] -> ok   -> [3,5,4,2,8]
        [5,4] -> swap -> [3,4,5,2,8]
        [5,2] -> swap -> [3,4,2,5,8]  ← 5 sudah di posisi benar

Pass 3: [3,4] -> ok   -> [3,4,2,5,8]
        [4,2] -> swap -> [3,2,4,5,8]  ← 4 sudah di posisi benar

Pass 4: [3,2] -> swap -> [2,3,4,5,8]  ✓ Selesai
```

---

### 2. Bubble Sort Optimized (Early Termination)

Perbedaannya hanya pada penambahan flag `swapped`. Jika dalam satu pass penuh tidak ada pertukaran, berarti array **sudah terurut** dan loop dihentikan lebih awal.

**Keunggulan nyata** pada array yang hampir terurut, misalnya `[1, 2, 4, 3, 5]`:

```
Pass 1: Hanya tukar [4,3] -> [1,2,3,4,5], swapped = true
Pass 2: Tidak ada swap -> swapped = false -> BREAK ✓

Basic version tetap jalan 4 pass meski sudah sorted.
```

---

### 3. Binary Search

Hanya bisa digunakan pada **array yang sudah terurut**. Strateginya adalah divide and conquer — selalu periksa elemen tengah, lalu putuskan arah pencarian (kiri atau kanan), sehingga ruang pencarian berkurang setengah di setiap langkah.

**Contoh** mencari `22` di `[11, 12, 22, 25, 34, 64, 90]`:

```
low=0, high=6 → mid=3, arr[3]=25 → 22 < 25 → high=2
low=0, high=2 → mid=1, arr[1]=12 → 22 > 12 → low=2
low=2, high=2 → mid=2, arr[2]=22 → FOUND! return 2 ✓

Total: hanya 3 langkah vs 7 langkah linear search.
```

> **Catatan penting:** `mid = low + (high - low) / 2` digunakan (bukan `(low+high)/2`) untuk **menghindari integer overflow** jika nilai `low` dan `high` sangat besar.

---

## Optimasi Algoritma

### Optimasi Bubble Sort: Shrinking Boundary (Cocktail Shaker Sort)

Selain early termination, ada optimasi lebih lanjut yang melacak **batas terakhir terjadinya swap**. Semua elemen di luar batas itu sudah pasti terurut, sehingga iterasi berikutnya tidak perlu menyentuhnya lagi.

```java
public static void bubbleSortShrinking(int[] arr) {
    int n = arr.length;
    int lastSwap = n - 1; // Batas aktif terakhir

    for (int i = 0; i < n - 1; i++) {
        int newLastSwap = 0; // Reset tracker swap terbaru

        for (int j = 0; j < lastSwap; j++) {
            if (arr[j] > arr[j + 1]) {
                int temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
                newLastSwap = j; // Catat posisi swap terakhir
            }
        }

        lastSwap = newLastSwap; // Batas baru — elemen setelahnya sudah sorted
        if (lastSwap == 0) break; // Tidak ada swap sama sekali
    }
}
```

**Perbandingan pass** untuk `[2, 1, 3, 4, 5, 6, 7]`:
```
Basic:     selalu loop sampai n-i-1 → 6 pass penuh
Optimized: early exit pass 2
Shrinking: lastSwap=0 setelah pass 1 → 1 pass saja ✓
```

---

### Optimasi Binary Search: Leftmost / Rightmost untuk Duplikat

Binary search standar hanya menemukan **salah satu** posisi jika ada elemen duplikat. Untuk kasus nyata, sering dibutuhkan posisi **pertama** atau **terakhir** dari elemen tersebut.

```java
// Cari posisi PERTAMA (leftmost) dari target
public static int binarySearchLeftmost(int[] arr, int target) {
    int low = 0, high = arr.length - 1, result = -1;

    while (low <= high) {
        int mid = low + (high - low) / 2;
        if (arr[mid] == target) {
            result = mid;    // Simpan kandidat
            high = mid - 1; // Terus cari ke KIRI
        } else if (arr[mid] < target) {
            low = mid + 1;
        } else {
            high = mid - 1;
        }
    }
    return result;
}

// Cari posisi TERAKHIR (rightmost) dari target
public static int binarySearchRightmost(int[] arr, int target) {
    int low = 0, high = arr.length - 1, result = -1;

    while (low <= high) {
        int mid = low + (high - low) / 2;
        if (arr[mid] == target) {
            result = mid;   // Simpan kandidat
            low = mid + 1;  // Terus cari ke KANAN
        } else if (arr[mid] < target) {
            low = mid + 1;
        } else {
            high = mid - 1;
        }
    }
    return result;
}
```

Contoh pada `[1, 3, 3, 3, 5, 7]`, cari `3`:
```
Standard binary search  → index 2 (acak, tergantung kondisi)
binarySearchLeftmost(3) → index 1 ✓ (posisi pertama)
binarySearchRightmost(3)→ index 3 ✓ (posisi terakhir)

Jumlah kemunculan = rightmost - leftmost + 1 = 3 - 1 + 1 = 3
```

---

## Ringkasan Kompleksitas

| Algoritma | Best | Average | Worst | Space |
|---|---|---|---|---|
| Bubble Sort Basic | O(n²) | O(n²) | O(n²) | O(1) |
| Bubble Sort Optimized | **O(n)** | O(n²) | O(n²) | O(1) |
| Bubble Sort Shrinking | **O(n)** | O(n²) | O(n²) | O(1) |
| Binary Search | O(1) | O(log n) | O(log n) | O(1) |
