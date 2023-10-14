import Calendar from '@douyinfe/semi-ui/lib/es/calendar'
import React from 'react'

const Index: React.FC = () => {

	const onClick = (e: Event, date: Date) =>{
		//
		console.log(e,date)
	}

    const importDates = [new Date(2023,10,1), new Date(2023,10,2), new Date(2023,10,3), new Date(2023,10,4)];

	const dateRender = dateString => {
		if (importDates.filter(date => date === dateString).length) {
            return <div style={{
				position: 'absolute',
				left: '0',
				right: '0',
				top: '0',
				bottom: '0',
				backgroundColor: 'var(--semi-color-danger-light-default)'
			}} />;
        }
		return null;
    };


	return <div><Calendar mode="month" onClick={onClick} dateGridRender={dateRender}></Calendar></div>
}

export default Index
