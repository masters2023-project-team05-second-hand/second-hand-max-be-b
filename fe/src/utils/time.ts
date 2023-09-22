const minuteInMs = 1000 * 60;
const hourInMs = minuteInMs * 60;
const dayInMs = hourInMs * 24;
const weekInMs = dayInMs * 7;
const monthInMs = weekInMs * 4;
const yearInMs = monthInMs * 12;

const timeUnits = [
  { unit: "방금 전", threshold: minuteInMs, divisor: 1 },
  { unit: "분 전", threshold: hourInMs, divisor: minuteInMs },
  { unit: "시간 전", threshold: dayInMs, divisor: hourInMs },
  { unit: "일 전", threshold: weekInMs, divisor: dayInMs },
  { unit: "주 전", threshold: monthInMs, divisor: weekInMs },
  { unit: "개월 전", threshold: yearInMs, divisor: monthInMs },
];

export const convertPastTimestamp = (timestamp: string) => {
  const startDate = new Date(timestamp);

  if (isNaN(startDate.getTime())) {
    throw new Error("Invalid timestamp");
  }

  const currDate = new Date();
  const diffMs = currDate.getTime() - startDate.getTime();

  for (const { unit, threshold, divisor } of timeUnits) {
    if (diffMs < threshold) {
      const value = Math.floor(diffMs / divisor);
      return threshold === minuteInMs ? `${unit}` : `${value}${unit}`;
    }
  }

  const year = startDate.getFullYear();
  const month = startDate.getMonth();
  const date = startDate.getDate();

  return `${year}/${month + 1}/${date}`;
};
